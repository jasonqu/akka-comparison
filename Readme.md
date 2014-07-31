java 实例来自from http://ifeve.com/

### Oracle官方并发教程

http://ifeve.com/oracle-java-concurrency-tutorial/

#### Thread Objects 线程对象

#####定义并启动一个线程

任务：创建线程打印hello world

com.oracle.sec2.thread_objects.HelloThread

定义并启动一个Actor也是很简单的

com.oracle.sec2.thread_objects.HelloAkka

#####使用Sleep方法暂停一个线程

任务：每四秒打印一个信息

com.oracle.sec2.thread_objects.SleepMessages

在Actor中，启动定时任务也是通过 system.scheduler 实现的，如果要结束定时任务，也需要通过程序触发

com.oracle.sec2.thread_objects.SimpleMessagesInAkka

#####中断（Interrupts）

Actor中没有中断的概念，对Actor来说，所有的动作都是离散的，所以只需要给它一个停止的消息，而该Actor进行相应的响应即可

#####Joins

Join()方法可以让一个线程等待另一个线程执行完成

Actor中没有Join，如果一个任务完成，可以通过消息通知到另一个Actor即可

#####一个简单的线程例子

下面这个简单的例子是对SleepMessages的改进，如果主线程等待一段时间后，打印线程仍未结束，就可以通过中断停止打印

这个例子 将会把这一节的一些概念放到一起演示。

MessageLoop线程将会打印一系列的信息。如果中断在它打印完所有信息前发生，它将会打印一个特定的消息并退出。

com.oracle.sec2.thread_objects.SimpleThreads

Akka的例子

com.oracle.sec2.thread_objects.SimpleThreadsInAkka


#### Synchronization 同步

##### 线程干扰

在Actor模型中，同一个Actor的消息处理都是单线程的，或者可以认为是同步的

即在处理完这个消息之前，是不会处理下一消息的

所以对这个Counter可以这样改写为Actor

case object Increase
case object Decrease
case object GetValue

class Counter extends Actor {
  var c = 0
  
  def receive = {
    case Increase => c += 1
    case Decrease => c -= 1
    case GetValue => sender ! c
  }
}

##### 内存一致性错误

确立 happens-before关系，对Actor来说就是消息顺序

TODO
有许多操作会建立happens-before关系。其中一个是同步，我们将在下面的章节中看到。

我们已经见过两个建立happens-before关系的操作。

当一条语句调用Thread.start方法时，和该语句有happens-before关系的每一条语句，跟新线程执行的每一条语句同样有happens-before关系。创建新线程之前的代码的执行结果对线新线程是可见的。
当一个线程终止并且当导致另一个线程中Thread.join返回时，被终止的线程执行的所有语句和在join返回成功之后的所有语句间有happens-before关系。线程中代码的执行结果对执行join操作的线程是可见的。
要查看建立happens-before关系的操作列表，请参阅java.util.concurrent包的摘要页面。

##### 同步方法

Actor方案见"线程干扰"

锁，我们都不关注

#####原子访问

有些操作你可以定义为原子的：

对引用变量和大部分基本类型变量（除long和double之外）的读写是原子的。
对所有声明为volatile的变量（包括long和double变量）的读写是原子的。

对Actor可以不关注？



### Liveness 活跃度

#### 死锁

Actor 模型中没有死锁

### Guarded Blocks

使用notify实现一个简单的生产者消费者模型

TODO Actor

###不可变对象

scala对于不可变很重视，case class的实例就是不可变的对象

TODO










### Java并发性和多线程









