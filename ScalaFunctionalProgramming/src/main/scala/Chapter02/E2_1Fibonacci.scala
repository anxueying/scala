package Chapter02

/**
  * @author Shelly An
  * @create 2020/7/21 19:35
  */
object E2_1Fibonacci {
  def main(args: Array[String]): Unit = {
    println(fib(5))
  }

  //@annotation.tailrec
  def fib(n:Int):Int={
    if(n==0){
      0
    }else if(n<=2){
      1
    }else{
      fib(n-2)+fib(n-1)
    }
  }
}
