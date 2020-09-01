package Chapter02

/**
  * @author Shelly An
  * @create 2020/7/21 20:23
  * 检测Array[A]是否按照给定的比较函数排序
  */
object E2_2Sort {
  def main(args: Array[String]): Unit = {
    val arr=Array[Int](2,3,5,6,7,20)
    println(isSorted(arr, (x: Int, y: Int) => x < y))
  }

  def isSorted[A](as:Array[A],ordered: (A,A)=>Boolean):Boolean={
    var result = true
    for (i <- 0 until as.length-1) {
      result = result && ordered(as(i),as(i+1))
    }
    result
  }
}
