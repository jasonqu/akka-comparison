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

线程安全及不可变性

7. 引用不是线程安全的！

重要的是要记住，即使一个对象是线程安全的不可变对象，指向这个对象的引用也可能不是线程安全的。

9. 线程通信

16. Java中的读/写锁

一个完整可重入的读写锁的实例：
http://ifeve.com/read-write-locks/

20. 线程池

有一个最简单的线程池的例子，akka也要使用线程池，不过akka的使用者无需关心


21. 剖析同步器

可以看到设计和实现一个同步器的时候需要考虑的问题是非常多的。actor模型不用关心这些。


基本不需要关注
###Java内存模型Cookbook
###Java内存模型FAQ
###同步和Java内存模型



##其它译文方面

###并发基础
####Java并发结构

当一个线程释放锁的时候，另一个线程可能正等待这个锁（也可能是同一个线程，因为这个线程可能需要进入另一个同步方法）。但是关于哪一个线程能够紧接着获得这个锁以及什么时候，这是没有任何保证的。（也就是，没有任何的公平性保证-见3.4.1.5）另外，没有什么办法能够得到一个给定的锁正被哪个线程拥有着。

正如2.2.7讨论的，除了锁控制之外，同步也会对底层的内存系统带来副作用。




####任务取消(Cancellation)

#####中断（Interruption）

有两种情况线程会保持休眠而无法检测中断状态或接收InterruptedException：在同步块中和在IO中阻塞时。线程在等待同步方法或同步块的锁时不会对中断有响应。但是，如§2.5中讨论的，当需要大幅降低在活动取消期间被卡在锁等待中的几率，可以使用lock工具类。使用lock类的代码阻塞仅是为了访问锁对象本身，而不是这些锁所保护的代码。这些阻塞的耗时天生就很短（尽管时间不能严格保证）。


#####IO和资源撤销（IO and resource revocation）

一些IO支持类（尤其是java.net.Socket及其相关类）提供了在读操作阻塞的时候能够超时的可选途径，在这种情况下就可以在超时后检测中断。java.io中的其它类采用了另一种方式——一种特殊形式的资源撤销。

CancellableReader
可以通过关闭IO对象和中断线程来完成活动取消。
使用interrupt中断io线程

ReaderWithTimeout
一个不好的实践

#####多步取消（Multiphase cancellation）

有时候，即使取消的是普通的代码，损害也比通常的更大。为应付这种可能性，可以建立一个通用的多步取消功能，尽可能尝试以破坏性最小的方式来取消任务，如果稍候还没有终止，再使用一种破坏性较大的方式。

Akka的关闭？TODO

####Java NIO与IO
####JVM运行时数据区
####happens-before


###The java.util.concurrent Synchronizer Framework 中文翻译版


###Java Fork Join 框架

可以看一下性能的部分

###Doug Lea并发编程文章全部译文

前面有几篇dl的都集中在一起了

###Mechanical Sympathy 译文

blog

cpu缓存

当你在设计一个重要算法时要记住，缓存不命中所导致的延迟，可能会使你失去执行500条指令时间！这还仅是在单插槽（single-socket）系统上，如果是多插槽(multi-socket)系统，由于内存访问需要跨槽交互，可能会导致双倍的性能损失。

####伪共享(False Sharing)

cache line 缓存填充



### Java虚拟机并发编程 之 软件事务内存导论




###聊聊并发系列


####聊聊并发（一）深入分析Volatile的实现原理
####聊聊并发（二）Java SE1.6中的Synchronized
####聊聊并发（三）Java线程池的分析和使用
跟前面的线程池的内容差不多
####聊聊并发（四）深入分析ConcurrentHashMap
线程不安全的HashMap 有一个详细分析，TODO
####聊聊并发（五）原子操作的实现原理
####聊聊并发（六）ConcurrentLinkedQueue的实现原理
####聊聊并发（七）Java中的阻塞队列
####聊聊并发（八）Fork/Join框架介绍
reserved for compare
####聊聊并发（九）Java中的CopyOnWrite容器
####聊聊并发（十）生产者消费者模式
两个例子，待参考


###深入理解java内存模型系列文章

####深入理解java内存模型（一）——基础

#####并发编程模型的分类
在并发编程中，我们需要处理两个关键问题：线程之间如何通信及线程之间如何同步（这里的线程是指并发执行的活动实体）。通信是指线程之间以何种机制来交换信息。在命令式编程中，线程之间的通信机制有两种：共享内存和消息传递。
在共享内存的并发模型里，线程之间共享程序的公共状态，线程之间通过写-读内存中的公共状态来隐式进行通信。在消息传递的并发模型里，线程之间没有公共状态，线程之间必须通过明确的发送消息来显式进行通信。
同步是指程序用于控制不同线程之间操作发生相对顺序的机制。在共享内存并发模型里，同步是显式进行的。程序员必须显式指定某个方法或某段代码需要在线程之间互斥执行。在消息传递的并发模型里，由于消息的发送必须在消息的接收之前，因此同步是隐式进行的。
Java的并发采用的是共享内存模型，Java线程之间的通信总是隐式进行，整个通信过程对程序员完全透明。如果编写多线程程序的Java程序员不理解隐式进行的线程之间通信的工作机制，很可能会遇到各种奇怪的内存可见性问题。

您好，原文简单概括如下：
并发编程要处理两个关键问题：通信和同步。
共享内存模型：显式同步，隐式通信。
消息传递模型：显式通信，隐式同步。
由于java的并发采用共享内存模型，所以java线程之间的通信是隐式进行的。

erlang这种并发语言就是采用消息传递的方式来通信的，其内部最一切都是线程就像java的一切都是对象一样，它的变量一经定义赋值就不可改变，不存在锁这种东西，为并发而存在

如果对java内存模型感兴趣的话，可以从下面三个文献入手：
JSR-133: Java Memory Model and Thread Specification 
The JSR-133 Cookbook for Compiler Writers 
JSR 133 (Java Memory Model) FAQ

Java的并发采用的是共享内存模型，共享内存模型的线程间通信是隐式进行的。
如果对于这个主题感兴趣的话，可以参阅Michael L. Scott的《Programming Language Pragmatics, Third Edition 》的第12章。


在涉及JSR-133的几个规范中，都不存在本地内存的概念：
《JSR-133: Java Memory Model and Thread Specification》，
《The Java™ Language Specification Third Edition》
《The Java™ Language Specification Java SE 7 Edition》，
《The Java™ Virtual Machine Specification Java SE 7 Edition》

本地内存以及这段话的描述，是从Brian Goetz写的一篇关于JSR-133内存模型的非常著名的文章中借鉴而来：
《Java theory and practice: Fixing the Java Memory Model, Part 2》
URL：www.ibm.com/developerworks/library/j-jtp03304/i...

Brian Goetz通过使用本地内存这个概念来抽象CPU，内存系统和编译器的优化。








####深入理解java内存模型（二）——重排序
####深入理解java内存模型（三）——顺序一致性
####深入理解java内存模型（四）——volatile
####深入理解java内存模型（五）——锁
ReentrantLock 代码分析
####深入理解java内存模型（六）——final
####深入理解java内存模型（七）——总结


###Java视角理解系统结构
####从Java视角理解系统结构（一）上下文切换
####从Java视角理解系统结构（二）CPU缓存
L1CacheMiss 
####从Java视角理解系统结构（三）伪共享
class FalseSharing



###Java 7 并发编程指南中文版

http://ifeve.com/java-7-concurrency-cookbook/


线程管理（八）在线程里处理不受控制的异常
http://ifeve.com/thread-management-9/

































