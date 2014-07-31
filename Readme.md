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







### Java并发性和多线程









