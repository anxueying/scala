package day02

import java.sql.{DriverManager, ResultSet}

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.{TableInputFormat, TableOutputFormat}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.rdd.{JdbcRDD, NewHadoopRDD}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}


/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */
class ReadAndWriteTest {

  @Test
  def writeHBaseTest(): Unit = {
    val list = List(("r3", ("cf1", "age", "20")), ("r3", ("cf1", "name", "Marry")),
      ("r4", ("cf1", "age", "15")), ("r4", ("cf1", "name", "Lily")))
    val rdd = sc.makeRDD(list, 2)
    //创建
    val conf = HBaseConfiguration.create()

    //设置当前要写到哪个表
    conf.set(TableOutputFormat.OUTPUT_TABLE, "t1")
    //在conf中设置各种参数
    val job = Job.getInstance(conf)
    //设置输出格式
    job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])
    //设置输出的key，value的类型
    job.setOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setOutputValueClass(classOf[Put])

    //MR中是mapper，reducer。。spark要把数据封装为上面的类型 key-value类型
    val rdd2 = rdd.map({
      case (rowkey, (cf, cq, v)) => {
        val key = new ImmutableBytesWritable()
        key.set(Bytes.toBytes(rowkey))

        val value = new Put(Bytes.toBytes(rowkey))
        value.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cq), Bytes.toBytes(v))

        (key, value)
      }
    })

    //把之前的设置传入
    rdd2.saveAsNewAPIHadoopDataset(job.getConfiguration)

  }


  @Test
  def readHBaseTest(): Unit = {
    /**
      * 1. connection
      * 2. table
      * 3. table.scan
      *
      * 在MR中读取HBase
      * Spark对标MR，因此用线程的输入格式（InputFormat）来读HBase
      */

    //创建
    val conf = HBaseConfiguration.create()

    //设置当前要读取哪个表
    conf.set(TableInputFormat.INPUT_TABLE, "t1")

    //result里包含rowkey，所以ImmutableBytesWritable要不要无所谓
    val rdd = new NewHadoopRDD[ImmutableBytesWritable, Result](sc, classOf[TableInputFormat],
      classOf[ImmutableBytesWritable], classOf[Result], conf)

    rdd.foreach({
      case (rowkey, result) => {
        //CellUtil  取出cell某个属性
        //获取所有的单元格
        val cells = result.rawCells()
        cells.foreach(x => {
          //Bytes 将Java中的数据类型转为byte[]  互转
          val str = Bytes.toString(CellUtil.cloneRow(x)) + " " + Bytes.toString(CellUtil.cloneFamily(x)) + " " +
            Bytes.toString(CellUtil.cloneQualifier(x)) + " " +
            Bytes.toString(CellUtil.cloneValue(x))
          println(str)
        })

      }
    })


  }


  /**
    * 写
    */
  @Test
  def mySqlWriteTest(): Unit = {
    val list = List[(String, String)](("3", "shelly"), ("4", "jason"))
    val rdd1 = sc.makeRDD(list, 2)

    rdd1.foreachPartition({ iter => {
      val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/myemployees", "root", "root")

      val sql = "  insert into student(sid,sname) values(?,?) "

      val ps = connect.prepareStatement(sql)

      iter.foreach {
        case (sid, sname) => {
          ps.setString(1, sid)
          ps.setString(2, sname)

          ps.executeUpdate()
        }
      }
      ps.close()
      connect.close()
    }
    })
  }


  /**
    * 读
    */
  @Test
  def mySqlReadTest(): Unit = {
    //核心：创建RDD 构建JDBC连接器连接到mysql
    val rdd = new JdbcRDD[String](sc,
      () => DriverManager.getConnection("jdbc:mysql://localhost:3306/myemployees", "root", "root"),
      "select * from employees where ? <= employee_id and employee_id <= ? ",
      110,
      116,
      2,
      (rs: ResultSet) => {
        //自动帮你调用next，自己别调用！！
        rs.getInt("employee_id") + " " + rs.getString("first_name")
      })

    rdd.foreach(println)
  }

  @Test
  def test(): Unit = {
    val list1 = List[Int](1, 2, 3, 4, 5)
    val rdd1 = sc.makeRDD(list1, 2)

    val rdd2 = rdd1.map((_, 1))
    rdd2.saveAsSequenceFile("outputSpark")

    val readResult = sc.sequenceFile[Int, Int]("outputSpark")
    readResult.foreach(println)
  }


  var sc: SparkContext = null

  /**
    * 每次运行前，自动删除输出目录 使用hadoop的包
    */
  @Before
  def start(): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("My app")

    sc = new SparkContext(conf)
    val fs = FileSystem.get(new Configuration())
    val path = new Path("outputSPark")
    if (fs.exists(path)) {
      //递归删除
      fs.delete(path, true)
    }
  }


  /**
    * 每次运行后，关闭资源
    */
  @After
  def stop() = {
    sc.stop()
  }
}
