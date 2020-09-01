package apps

import DAO.core.TApplication
import wordcount.WordCountController

/**
  * @author Shelly An
  * @create 2020/7/29 10:23
  *        封装环境（每个应用程序一样）
  *        封装逻辑（每个应用程序不一样）
  */
object WordCount3 extends App with TApplication {

    execute{
      //创建控制器，执行调度  在spring中可以通过底层的反射机制动态创建，不需要new，耦合性更低
      val controller = new WordCountController()
      controller.dispatcher()
    }
}
