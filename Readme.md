java 实例来自from http://ifeve.com/

### Oracle官方并发教程

http://ifeve.com/oracle-java-concurrency-tutorial/

#### Thread Objects 线程对象

#####定义并启动一个线程

任务：创建线程打印hello world

com.oracle.sec2.thread_objects.HelloThread

定义并启动一个Actor也是很简单的

com.oracle.sec2.thread_objects.HelloAkka

如java线程不同，akka执行完成后是不会自动退出的，需要手动杀进程，一般我们也不需要自动退出【TODO 自动退出程序？】

#####使用Sleep方法暂停一个线程

任务：每四秒打印一个信息

com.oracle.sec2.thread_objects.SleepMessages

在Actor中，启动定时任务也是通过 system.scheduler 实现的，如果要结束定时任务，也需要通过程序触发

com.oracle.sec2.thread_objects.SimpleMessagesInAkka

还有更简化的写法，只用一个Actor

com.oracle.sec2.thread_objects.SimpleMessageLoop2

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

即在处理完这个消息之前，是不会处理下一消息的

##### 同步方法、内部锁与同步、原子访问

Actor模型 都不关注，天然无风险


### Liveness 活跃度

#### 死锁

任务：每一个人被鞠躬之后都要鞠躬回来以示礼节。但是这段代码由于使用了相同的锁，导致了死锁情况的出现

com.oracle.sec4.liveness.Deadlock

Actor 模型中没有使用锁，所以不存在死锁

com.oracle.sec4.liveness.DeadlockInAkka

####饥饿和活锁

归功于Actor模型的无锁特性，这两种情况在Akka中也不存在

### Guarded Blocks

多线程之间经常需要协同工作，最常见的方式是使用Guarded Blocks，它循环检查一个条件（通常初始值为true），直到条件发生变化才跳出循环继续执行。

使用notify实现一个简单的生产者消费者模型：

资源是字符串，一个资源槽只能容纳一个资源。
一个生产者隔一秒生成一个字符串需要放到资源槽中，如果槽满就等待，直到被唤醒。最后放入Done标识结束
一个消费者隔一秒从资源槽中获取并消费一个字符串，如果槽空就等待，直到被唤醒。最后收到Done标识结束

com.oracle.sec5.guarded_blocks.ProducerConsumerExample

Actor模型相对简单，一个Master通过消息控制生产者的生产和消费者的消费，达到相同效果

com.oracle.sec5.guarded_blocks.ProducerConsumerExampleInAkka

###Immutable Objects 不可变对象

scala对于不可变很重视，case class的实例就是不可变的对象

可以看到scala中定义一个 ImmutableRGB 比 java 中要简洁很多

com.oracle.sec6.immutable.ImmutableRGBInScala




###High Level Concurrency Objects 高级并发对象

####锁对象

Akka不需要锁

####执行器（Executors）、线程池

Akka底层集成了java的Executors和线程池，所以我们可以不关注这些细节，Akka会自动利用系统资源进行多线程的处理

http://doc.akka.io/docs/akka/2.3.4/scala/dispatchers.html

####Fork/Join

任务：模糊一张图片

com.oracle.sec7.executors.ForkBlur

标准实现中也大量运用了Fork/Join来做，包括java8中 java.util.Arrays类的一系列parallelSort()方法等。

其他采用了fork/join框架的方法还包括java.util.streams包中的一些方法，此包是作为Java SE 8发行版中Project Lambda的一部分。

Akka原生支持 Fork/Join 处理模式，可以在配置中选择该模式
http://doc.akka.io/docs/akka/2.3.4/scala/dispatchers.html

akka版 todo


#### 并发集合 原子变量 并发随机数 略


### Further Reading 略

### Questions and Exercises

TODO





### Java并发性和多线程









