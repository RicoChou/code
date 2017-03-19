##### 其他1. junit 用法，before, beforeClass, after, afterClass 的执行顺序

> **@Before**：初始化方法   对于每一个测试方法都要执行一次（注意与BeforeClass区别，后者是对于所有方法执行一次）

> **@After**：释放资源  对于每一个测试方法都要执行一次（注意与AfterClass区别，后者是对于所有方法执行一次）

>**@BeforeClass**：针对所有测试，只执行一次，且必须为static void

>**@AfterClass**：针对所有测试，只执行一次，且必须为static void

> 一个JUnit4的单元测试用例执行顺序为：
@BeforeClass -> @Before -> @Test -> @After -> @AfterClass;

##### 多线程2. [分布式锁][DST_LOCK]
> [zookeeper][ZOOKEEPER] 实现：基于 zookeeper 瞬时有序节点实现的分布式锁，大致思想即为：每个客户端对某个功能加锁时，在 zookeeper 上的与该功能对应的指定节点的目录下，生成一个唯一的瞬时有序节点。判断是否获取锁的方式很简单，只需要判断有序节点中序号最小的一个。当释放锁的时候，只需将这个瞬时节点删除即可。同时，其可以避免服务宕机导致的锁无法释放，而产生的死锁问题。**优点：**锁安全性高，zk 可持久化。**缺点：**性能开销比较高。因为其需要动态产生、销毁瞬时节点来实现锁功能。

> **memcached** 实现：memcached 带有 add 函数，利用 add 函数的特性即可实现分布式锁。add 和 set 的区别在于：如果多线程并发 set ，则每个 set 都会成功，但最后存储的值以最后的 set 的线程为准。而 add 的话则相反，add 会添加第一个到达的值，并返回 true，后续的添加则都会返回 false。利用该点即可很轻松地实现分布式锁。**优点：**并发高效。 **缺点：** （1）memcached 采用列入 LRU 置换策略，所以如果内存不够，可能导致缓存中的锁信息丢失。（2）memcached 无法持久化，一旦重启，将导致信息丢失。

> **redis**实现：redis 分布式锁即可以结合 zk 分布式锁锁高度安全和 memcached 并发场景下效率很好的优点，可以利用 jedis 客户端实现。参考 [Redis 实现分布式锁][REDIS_DST_LOCK]

##### 其他3. [Nginx][NGINX_TAOBAO] 的请求转发算法，如何配置根据权重转发
[NGINX_TAOBAO]: http://tengine.taobao.org/book/
- **round-robin：**意思是循环轮询，默认算法，Nginx会把第1个请求给10.10.10.1，把第2个请求给10.10.10.2，第3个请求给10.10.10.1，以此类推。
- **least-connected：**最少连接，即每次都找连接数最少的服务器来转发请求。当 Nginx 接收到一个请求时，A正在处理的请求数是10，B正在处理的请求数是20，则 Nginx 会把当前请求交给A来处理。要启用最少连接负载算法只需要在定义服务器组时加上 least_conn ：
```C
upstream app1 {
    least_conn;
    server 10.10.10.1;
    server 10.10.10.2;
}
```
- **ip-hash：**算法会根据请求的客户端 IP 地址来决定当前请求应该交给谁。使用 ip-hash 算法时 Nginx 会确保来自同一客户端的请求都分发到同一服务器。要使用 ip-hash 算法时只需要在定义服务器组时加上 ip-hash 指令：
```C
upstream app1 {
    ip_hash;
    server 10.10.10.1;
    server 10.10.10.2;
}
```
- **weighted：**算法也就是权重算法，会根据每个服务的权重来分发请求，权重大的请求相对会多分发一点，权重小的会少分发一点。这通常应用于多个服务器的性能不一致时。需要使用权重算法时只需要在定义服务器组时在服务器后面指定参数 weight：
```C
upstream app1 {
    server 10.10.10.1 weight=3;
    server 10.10.10.2;
}
```

##### java4. 用hashmap实现Redis有什么问题（死锁，死循环，可用ConcurrentHashmap）
[容量和多线程并发问题][HASHMAP_REDIS]
[HASHMAP_REDIS]: http://blog.csdn.net/mydreamongo/article/details/8917843
另：[Java 和 Redis 中 Hashmap 异同][HASHMAP_DIFF]
[HASHMAP_DIFF]: http://www.cnblogs.com/ironPhoenix/p/6048477.html
##### 多线程5. 线程的状态，以及线程的阻塞的方式
[线程的五种状态以及改变状态的三种方法](http://www.cnblogs.com/garfieldcgf/p/5518929.html)
![PIC_THREAD_STATES][PIC_THREAD_STATES]
[PIC_THREAD_STATES]: http://www.blogjava.net/images/blogjava_net/santicom/360%E6%88%AA%E5%9B%BE20110901211600850.jpg
1. 新建状态（New）：
当用 new 操作符创建一个线程时， 例如 new Thread(r)，线程还没有开始运行，此时线程处在新建状态。 当一个线程处于新生状态时，程序还没有开始运行线程中的代码

2. 就绪状态（Runnable）：
一个新创建的线程并不自动开始运行，要执行线程，必须调用线程的 start() 方法。当线程对象调用 start() 方法即启动了线程，start() 方法创建线程运行的系统资源，并调度线程运行 run() 方法。**当 start() 方法返回后**，线程就处于就绪状态。处于就绪状态的线程并不一定立即运行 run() 方法，线程还必须同其他线程竞争 CPU 时间，只有获得 CPU 时间才可以运行线程。因为在单 CPU 的计算机系统中，不可能同时运行多个线程，一个时刻仅有一个线程处于运行状态。因此此时可能有多个线程处于就绪状态。对多个处于就绪状态的线程是由 Java 运行时系统的线程调度程序（thread scheduler）来调度的。

3. 运行状态（Running）：
当线程获得 CPU 时间后，它才进入运行状态，真正开始执行 run() 方法.

4. 阻塞状态（Blocked）：
线程运行过程中，可能由于各种原因进入阻塞状态：

    （1）线程通过调用sleep方法进入睡眠状态；
    （2）线程调用一个在I/O上被阻塞的操作，即该操作在输入输出操作完成之前不会返回到它的调用者；
    （3）线程试图得到一个锁，而该锁正被其他线程持有；
    （4）线程在等待某个触发条件；
    等等

    所谓阻塞状态是正在运行的线程没有运行结束，暂时让出 CPU ，这时其他处于就绪状态的线程就可以获得 CPU 时间，进入运行状态。

5. 死亡状态（Dead）：
有两个原因会导致线程死亡：
（1） run() 方法正常退出而自然死亡；
（2） 一个未捕获的异常终止了 run() 方法而使线程猝死。
为了确定线程在当前是否存活着（就是要么是可运行的，要么是被阻塞了），需要使用 isAlive() 方法。如果是可运行或被阻塞，这个方法返回true；如果线程仍旧是 new 状态且不是可运行的， 或者线程死亡了，则返回 false。

##### 多线程6. sleep和wait的区别
都用来进行线程控制,他们最大本质的区别是:sleep()不释放同步锁,wait()释放同步缩。
  还有用法的上的不同是:sleep(milliseconds)可以用时间指定来使他自动醒过来,如果时间不到你只能调用interreput()来强行打断;wait()可以用notify()直接唤起.

  sleep是Thread类的静态方法。sleep的作用是让线程休眠制定的时间，在时间到达时恢复，也就是说sleep将在接到时间到达事件事恢复线程执行，例如：


7. hashmap的底层实现
8. 一万个人抢100个红包，如何实现（不用队列），如何保证2个人不能抢到同一个红包，可用分布式锁
9. java内存模型，垃圾回收机制，不可达算法
10. 两个Integer的引用对象传给一个swap方法在方法内部交换引用，返回后，两个引用的值是否会发现变化

11. aop的底层实现，动态代理是如何动态，假如有100个对象，如何动态的为这100个对象代理
12. 是否用过maven install。 maven test。Git（make install是安装本地jar包）
13. tomcat的各种配置，如何配置docBase
14. spring的bean配置的几种方式
##### java15. web.xml的配置
<servlet>
    <servlet-name>servlet1</servlet-name>
    <servlet-class>net.test.TestServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>servlet1</servlet-name>
    <url-pattern>*.do</url-pattern>
</servlet-mapping>


16. spring的监听器。
17. zookeeper的实现机制，有缓存，如何存储注册服务的
18. IO会阻塞吗？readLine是不是阻塞的
19. 用过spring的线程池还是java的线程池？
20. 字符串的格式化方法 （20，21这两个问题问的太低级了）



21. 时间的格式化方法
22. 定时器用什么做的
23. 线程如何退出结束
24. java有哪些锁？乐观锁 悲观锁 synchronized 可重入锁 读写锁,用过reentrantlock吗？reentrantlock与synmchronized的区别
25. ThreadLocal的使用场景
26. java的内存模型，垃圾回收机制
27. 为什么线程执行要调用start而不是直接run（直接run，跟普通方法没什么区别，先调start，run才会作为一个线程方法运行）
28. qmq消息的实现机制(qmq是去哪儿网自己封装的消息队列)
##### java29. 遍历hashmap的三种方式
//第一种：普遍使用，二次取值
System.out.println("通过Map.keySet遍历key和value：");
for (String key : map.keySet()) {
 System.out.println("key= "+ key + " and value= " + map.get(key));
}

//第二种
System.out.println("通过Map.entrySet使用iterator遍历key和value：");
Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
while (it.hasNext()) {
 Map.Entry<String, String> entry = it.next();
 System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
}

//第三种：推荐，尤其是容量大时
System.out.println("通过Map.entrySet遍历key和value");
for (Map.Entry<String, String> entry : map.entrySet()) {
 System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
}

//第四种
System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
for (String v : map.values()) {
 System.out.println("value= " + v);
}
}

##### java30. jvm的一些命令
http://blog.csdn.net/pistolove/article/details/52032501


##### 其他31. memcache和redis的区别
http://www.cnblogs.com/maweiba/p/6089664.html
http://blog.csdn.net/buquan4041/article/details/52832552?fps=1&locationNum=5

##### sql32. MySQL的行级锁加在哪个位置
InnoDB行锁是通过给索引上的索引项加锁来实现的
INNODB的行级锁有共享锁（S LOCK）和排他锁（X LOCK）两种。共享锁允许事物读一行记录，不允许任何线程对该行记录进行修改。排他锁允许当前事物删除或更新一行记录，其他线程不能操作该记录。

##### java33. ConcurrentHashmap的锁是如何加的？是不是分段越多越好
通过分析Hashtable就知道，synchronized是针对整张Hash表的，即每次锁住整张表让线程独占，ConcurrentHashMap允许多个修改操作并发进行，其关键在于使用了锁分离技术。它使用了多个锁来控制对hash表的不同部分进行的修改。ConcurrentHashMap内部使用段(Segment)来表示这些不同的部分，每个段其实就是一个小的hash table，它们有自己的锁。只要多个修改操作发生在不同的段上，它们就可以并发进行。
有些方法需要跨段，比如size()和containsValue()，它们可能需要锁定整个表而而不仅仅是某个段，这需要按顺序锁定所有段，操作完毕后，又按顺序释放所有段的锁。这里“按顺序”是很重要的，否则极有可能出现死锁，在ConcurrentHashMap内部，段数组是final的，并且其成员变量实际上也是final的，但是，仅仅是将数组声明为final的并不保证数组成员也是final的，这需要实现上的保证。这可以确保不会出现死锁，因为获得锁的顺序是固定的。http://www.cnblogs.com/ITtangtang/p/3948786.html ,,,,http://www.iteye.com/topic/344876

34. myisam和innodb的区别（innodb是行级锁，myisam是表级锁）
##### sql35. mysql其他的性能优化方式
1、创建索引
对于查询占主要的应用来说，索引显得尤为重要。很多时候性能问题很简单的就是因为我们忘了添加索引而造成的，或者说没有添加更为有效的索引导致。如果不加索引的话，那么查找任何哪怕只是一条特定的数据都会进行一次全表扫描，如果一张表的数据量很大而符合条件的结果又很少，那么不加索引会引起致命的性能下降。但是也不是什么情况都非得建索引不可，比如性别可能就只有两个值，建索引不仅没什么优势，还会影响到更新速度，这被称为过度索引。
2、复合索引
比如有一条语句是这样的：select * from users where area='beijing' and age=22;
如果我们是在area和age上分别创建单个索引的话，由于mysql查询每次只能使用一个索引，所以虽然这样已经相对不做索引时全表扫描提高了很多效率，但是如果在area、age两列上创建复合索引的话将带来更高的效率。如果我们创建了(area, age, salary)的复合索引，那么其实相当于创建了(area,age,salary)、(area,age)、(area)三个索引，这被称为最佳左前缀特性。因此我们在创建复合索引时应该将最常用作限制条件的列放在最左边，依次递减。
3、索引不会包含有NULL值的列
只要列中包含有NULL值都将不会被包含在索引中，复合索引中只要有一列含有NULL值，那么这一列对于此复合索引就是无效的。所以我们在数据库设计时不要让字段的默认值为NULL。
4、使用短索引
对串列进行索引，如果可能应该指定一个前缀长度。例如，如果有一个CHAR(255)的 列，如果在前10 个或20 个字符内，多数值是惟一的，那么就不要对整个列进行索引。短索引不仅可以提高查询速度而且可以节省磁盘空间和I/O操作。
5、排序的索引问题
查询只使用一个索引，因此如果where子句中已经使用了索引的话，那么order by中的列是不会使用索引的。因此数据库默认排序可以符合要求的情况下不要使用排序操作；尽量不要包含多个列的排序，如果需要最好给这些列创建复合索引。
6、like语句操作
一般情况下不鼓励使用like操作，如果非使用不可，如何使用也是一个问题。like “%aaa%” 不会使用索引而like “aaa%”可以使用索引。
7、不要在列上进行运算
select * from users where YEAR(adddate)<2007;
将在每个行上进行运算，这将导致索引失效而进行全表扫描，因此我们可以改成
select * from users where adddate<'2007-01-01';
8、不使用NOT IN和<>操作
NOT IN和 '<>' 操作都不会使用索引将进行全表扫描。NOT IN可以NOT EXISTS代替，id<>3则可使用id>3 or id<3来代替。


36. Linux系统日志在哪里看
/var/log/messages,user,auth

37. 如何查看网络进程

38. 统计一个整数的二进制表示中bit为1的个数

39. jvm内存模型，java内存模型

##### java40. 如何把java内存的数据全部dump出来
jmap -dump:format=b,file=文件名 [pid]

41. 如何手动触发全量回收垃圾，如何立即触发垃圾回收

42. hashmap如果只有一个写其他全读会出什么问题

43. git rebase

44. MongoDB和Hbase的区别

45. 如何解决并发问题
46. volatile的用途
47. java线程池（好像之前我的理解有问题）
48. mysql的binlog
49. 代理模式
##### sql50. mysql是如何实现事务的
【1】Redo Log
在Innodb存储引擎中，事务日志是通过redo和innodb的存储引擎日志缓冲（Innodb log buffer）来实现的，当开始一个事务的时候，会记录该事务的lsn(log sequence number)号; 当事务执行时，会往InnoDB存储引擎的日志
的日志缓存里面插入事务日志；当事务提交时，必须将存储引擎的日志缓冲写入磁盘（通过innodb_flush_log_at_trx_commit来控制），也就是写数据前，需要先写日志。这种方式称为“预写日志方式”，
innodb通过此方式来保证事务的完整性。也就意味着磁盘上存储的数据页和内存缓冲池上面的页是不同步的，是先写入redo log，然后写入data file，因此是一种异步的方式。

【2】Undo
undo的记录正好与redo的相反，insert变成delete，update变成相反的update，redo放在redo file里面。而undo放在一个内部的一个特殊segment上面，存储与共享表空间内（ibdata1或者ibdata2中）。
undo不是物理恢复，是逻辑恢复，因为它是通过执行相反的dml语句来实现的。而且不会回收因为insert和upate而新增加的page页的。
undo页的回收是通过master thread线程来实现的。

51. 读写分离何时强制要读主库，读哪个从库是通过什么方式决定的，从库的同步mysql用的什么方式
52. mysql的存储引擎
##### sql53. mysql的默认隔离级别，其他隔离级别
Repeatable Read 可重复读


54. 将一个链表反转（用三个指针，但是每次只发转一个）
55. spring Aop的实现原理，具体说说
56. 何时会内存泄漏，内存泄漏会抛哪些异常
57. 是否用过Autowire注解
58. spring的注入bean的方式
##### sql59. sql语句各种条件的执行顺序，如select， where， order by， group by
from--where--group by--having--select--order by,
1、from子句组装来自不同数据源的数据；
　　2、where子句基于指定的条件对记录行进行筛选；
　　3、group by子句将数据划分为多个分组；
　　4、使用聚集函数进行计算；
　　5、使用having子句筛选分组；
　　6、计算所有的表达式；
　　7、使用order by对结果集进行排序。

60. select  xx from xx where xx and xx order by xx limit xx； 如何优化这个（看explain）

61. 四则元算写代码

62. 统计100G的ip文件中出现ip次数最多的100个ip
63. zookeeper的事物，结点，服务提供方挂了如何告知消费方


64. 5台服务器如何选出leader(选举算法)

65. 适配器和代理模式的区别
66. 读写锁
67. static加锁
##### sql68. 事务隔离级别
读未提交 Read-Uncommitted	0	导致脏读
读已提交 Read-Committed	1	避免脏读，允许不可重复读和幻读
可重复读 Repeatable-Read	2	避免脏读，不可重复读，允许幻读
串行化   Serializable	3	串行化读，事务只能一个一个执行，避免了脏读、不可重复读、幻读。执行效率慢，使用时慎重
脏读：一事务对数据进行了增删改，但未提交，另一事务可以读取到未提交的数据。如果第一个事务这时候回滚了，那么第二个事务就读到了脏数据。

不可重复读：一个事务中发生了两次读操作，第一次读操作和第二次操作之间，另外一个事务对数据进行了修改，这时候两次读取的数据是不一致的。

幻读：第一个事务对一定范围的数据进行批量修改，第二个事务在这个范围增加一条数据，这时候第一个事务就会丢失对新增数据的修改。
原子性（Atomicity）：事务是一个原子操作单元，其对数据的修改，要么全部执行，要么全都不执行；
一致性（Consistent）：在事务开始和完成时，数据都必须保持一致状态；
隔离性（Isolation）：数据库系统提供一定的隔离机制，保证事务在不受外部并发操作影响的“独立”环境执行；
持久性（Durable）：事务完成之后，它对于数据的修改是永久性的，即使出现系统故障也能够保持。

69. 门面模式，类图(外观模式)
70. mybatis如何映射表结构

71. 二叉树遍历
72. 主从复制
73. mysql引擎区别
74. 静态内部类加载到了哪个区？方法区

75. class文件编译后加载到了哪

76. web的http请求如何整体响应时间变长导致处理的请求数变少，该如何处理？用队列，当处理不了那么多http请求时将请求放到队列
中慢慢处理，web如何实现队列

##### 多线程77. 线程安全的单例模式
DCL加锁模式，私有静态类
if (instance == null) {
    sychronized (instance) {
        if (instance == null)
            instance = new Singleton();
    }
}

/* 此处使用一个内部类来维护单例 */
 private static class SingletonFactory {
     private static Singleton instance = new Singleton();
 }

78. 快速排序性能考虑

79. volatile关键字用法

80. 求表的size，或做数据统计可用什么存储引擎


##### sql81. 读多写少可用什么引擎
MyISAM


82. 假如要统计多个表应该用什么引擎

##### 多线程83. concurrenhashmap求size是如何加锁的，如果刚求完一段后这段发生了变化该如何处理
http://www.cnblogs.com/ITtangtang/p/3948786.html
因为在累加count操作过程中，之前累加过的count发生变化的几率非常小，所以ConcurrentHashMap的做法是先尝试2次通过不锁住Segment的方式来统计各个Segment大小，如果统计的过程中，容器的count发生了变化，则再采用加锁的方式来统计所有Segment的大小。

那么ConcurrentHashMap是如何判断在统计的时候容器是否发生了变化呢？使用modCount变量，在put , remove和clean方法里操作元素前都会将变量modCount进行加1，那么在统计size前后比较modCount是否发生变化，从而得知容器的大小是否发生变化。


##### 其他84. 1000个苹果放10个篮子，怎么放，能让我拿到所有可能的个数
2^10 = 1024, 拿与不拿对应两种状态，恰好符合二进制，所以10个篮子放 1, 2, 4, 8 ... 512 - 24


##### java85. 可重入的读写锁，可重入是如何实现的？
ReentrantLock实现细节
ReentrantLock支持两种获取锁的方式，一种是公平模型，一种是非公平模型
http://blog.csdn.net/yanyan19880509/article/details/52345422
可重入实现采用计数，重入加1，释放减1，计数为0时，释放锁，其他线程可获取锁进入代码块


##### java86. 是否用过NIO
http://blog.csdn.net/hxpjava1/article/details/56282385
Channels and Buffers（通道和缓冲区）：标准的IO基于字节流和字符流进行操作的，而NIO是基于通道（Channel）和缓冲区（Buffer）进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。
Asynchronous IO（异步IO）：Java NIO可以让你异步的使用IO，例如：当线程从通道读取数据到缓冲区时，线程还是可以进行其他事情。当数据被写入到缓冲区时，线程可以继续处理它。从缓冲区写入通道也类似。
Selectors（选择器）：Java NIO引入了选择器的概念，选择器用于监听多个通道的事件（比如：连接打开，数据到达）。因此，单个的线程可以监听多个数据通道。

87. java的concurrent包用过没

##### java88. sting s=new string("abc")分别在堆栈上新建了哪些对象
两个(一个是"abc",一个是指向"abc"的引用对象s)其实不对
两个，一个是字符串字面量"abc"所对应的、驻留（intern）在一个全局共享的字符串常量池中的实例，另一个是通过new String(String)创建并初始化的、内容与"abc"相同的实例

##### jvm89. java虚拟机的区域分配，各区分别存什么

https://www.zhihu.com/question/29884421/answer/113785601
https://pic4.zhimg.com/a5fb2f8f898ada8665b86fa0eb7038c3_b.jpg

http://blog.csdn.net/u012481172/article/details/50936815
Java中几种常量池的区分
http://tangxman.github.io/2015/07/27/the-difference-of-java-string-pool/

1、局部变量表
局部标量表 是一组变量值的存储空间，用于存放 方法参数 和 局部变量。在Class 文件的方法表的 Code 属性的 max_locals 指定了该方法所需局部变量表的最大容量。
变量槽 （Variable Slot）是局部变量表的最小单位，没有强制规定大小为 32 位，虽然32位足够存放大部分类型的数据。一个 Slot 可以存放 boolean、byte、char、short、int、float、reference 和 returnAddress 8种类型。其中 reference 表示对一个对象实例的引用，通过它可以得到对象在Java 堆中存放的起始地址的索引和该数据所属数据类型在方法区的类型信息。returnAddress 则指向了一条字节码指令的地址。 对于64位的 long 和 double 变量而言，虚拟机会为其分配两个连续的 Slot 空间。

2、操作数栈
操作数栈（Operand Stack）也常称为操作栈，是一个后入先出栈。在Class 文件的Code 属性的 max_stacks 指定了执行过程中最大的栈深度。Java 虚拟机的解释执行引擎称为”基于栈的执行引擎“，这里的栈就是指操作数栈。
方法执行中进行算术运算或者是调用其他的方法进行参数传递的时候是通过操作数栈进行的。
在概念模型中，两个栈帧是相互独立的。但是大多数虚拟机的实现都会进行优化，令两个栈帧出现一部分重叠。令下面的部分操作数栈与上面的局部变量表重叠在一块，这样在方法调用的时候可以共用一部分数据，无需进行额外的参数复制传递。
3、动态连接
每个栈帧都包含一个执行运行时常量池中该栈帧所属方法的引用，持有这个引用是为了支持方法调用过程中的动态连接（Dynamic Linking）。
Class 文件中存放了大量的符号引用，字节码中的方法调用指令就是以常量池中指向方法的符号引用作为参数。这些符号引用一部分会在类加载阶段或第一次使用时转化为直接引用，这种转化称为静态解析。另一部分将在每一次运行期间转化为直接引用，这部分称为动态连接。
4、方法返回地址
当一个方法开始执行以后，只有两种方法可以退出当前方法：
当执行遇到返回指令，会将返回值传递给上层的方法调用者，这种退出的方式称为正常完成出口（Normal Method Invocation Completion），一般来说，调用者的PC计数器可以作为返回地址。
当执行遇到异常，并且当前方法体内没有得到处理，就会导致方法退出，此时是没有返回值的，称为异常完成出口（Abrupt Method Invocation Completion），返回地址要通过异常处理器表来确定。
当方法返回时，可能进行3个操作：
恢复上层方法的局部变量表和操作数栈
把返回值压入调用者调用者栈帧的操作数栈
调整 PC 计数器的值以指向方法调用指令后面的一条指令
5、附加信息
虚拟机规范并没有规定具体虚拟机实现包含什么附加信息，这部分的内容完全取决于具体实现。在实际开发中，一般会把动态连接，方法返回地址和附加信息全部归为一类，称为栈帧信息。


90. 分布式事务（JTA）

91. threadlocal使用时注意的问题（ThreadLocal和Synchonized都用于解决多线程并发访问。但是ThreadLocal与synchronized有本质的区别。synchronized是利用锁的机制，使变量或代码块在某一时该只能被一个线程访问。而ThreadLocal为每一个线程都提供了变量的副本，使得每个线程在某一时间访问到的并不是同一个对象，这样就隔离了多个线程对数据的数据共享。而Synchronized却正好相反，它用于在多个线程间通信时能够获得数据共享）
线程安全，线程

92. java有哪些容器(集合，tomcat也是一种容器)

93. 二分查找算法
##### sql94. myisam的优点，和innodb的区别
1)MyISAM类型不支持事务处理等高级处理，而InnoDB类型支持

2)mysiam表不支持外键

3)在执行数据库写入的操作（insert,update,delete）的时候，mysiam表会锁表，而innodb表会锁行

4)当你的数据库有大量的写入、更新操作而查询比较少或者数据完整性要求比较高的时候就选择innodb表。当你的数据库主要以查询为主，相比较而言更新和写 入比较少，并且业务方面数据完整性要求不那么严格，就选择mysiam表。因为mysiam表的查询操作效率和速度都比innodb要快

##### java95. redis能存哪些类型
String、Hash、List、Set和Sorted Set

96. http协议格式，get和post的区别
请求行、消息报头、请求正文
状态行、消息报头、响应正文

97. 可重入锁中对应的wait和notify
98. redis能把内存空间交换进磁盘中吗(这个应该是可以的，但是那个面试官非跟我说不可以)
99. java线程池中基于缓存和基于定长的两种线程池，当请求太多时分别是如何处理的？定长的事用的队列，如果队列也满了呢？交换进磁盘？基于缓存的线程池解决方法呢？
##### 多线程100. synchronized加在方法上用的什么锁
各种锁 http://blog.csdn.net/a314773862/article/details/54095819
静态方法上是 类锁
实例方法上是 对象锁

##### 多线程101. 可重入锁中的lock和trylock的区别
作者：郭无心
链接：https://www.zhihu.com/question/36771163/answer/68974735
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

1）lock(), 拿不到lock就不罢休，不然线程就一直block。 比较无赖的做法。
2）tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。    带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
3）lockInterruptibly()就稍微难理解一些。先说说线程的打扰机制，每个线程都有一个 打扰 标志。这里分两种情况，1. 线程在sleep或wait,join， 此时如果别的进程调用此进程的 interrupt（）方法，此线程会被唤醒并被要求处理InterruptedException；(thread在做IO操作时也可能有类似行为，见java thread api)2. 此线程在运行中， 则不会收到提醒。但是 此线程的 “打扰标志”会被设置， 可以通过isInterrupted()查看并 作出处理。lockInterruptibly()和上面的第一种情况是一样的， 线程在请求lock并被阻塞时，如果被interrupt，则“此线程会被唤醒并被要求处理InterruptedException”。并且如果线程已经被interrupt，再使用lockInterruptibly的时候，此线程也会被要求处理interruptedException

102. innodb对一行数据的读会枷锁吗？不枷锁，读实际读的是副本
103. redis做缓存是分布式存的？不同的服务器上存的数据是否重复？guava cache呢？是否重复？不同的机器存的数据不同
104. 用awk统计一个ip文件中top10
105. 对表做统计时可直接看schema info信息，即查看表的系统信息
106. mysql目前用的版本
107. 公司经验丰富的人给了什么帮助？(一般boss面会问这些)
108. 自己相对于一样的应届生有什么优势
109. 自己的好的总结习惯给自己今后的工作带了什么帮助，举例为证

110. 原子类，线程安全的对象，异常的处理方式

111. 4亿个int数，如何找出重复的数（用hash方法，建一个2的32次方个bit的hash数组，每取一个int数，可hash下2的32次方找到它在hash数组中的位置，然后将bit置1表示已存在）
112. 4亿个url，找出其中重复的（考虑内存不够，通过hash算法，将url分配到1000个文件中，不同的文件间肯定就不会重复了，再分别找出重复的）
有1万个数组，每个数组有1000个整数，每个数组都是降序的，从中找出最大的N个数，N<1000

113. LinkedHashmap的底层实现
114. 类序列化时类的版本号的用途，如果没有指定一个版本号，系统是怎么处理的？如果加了字段会怎么样？

简单来说，Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。

当实现java.io.Serializable接口的实体（类）没有显式地定义一个名为serialVersionUID，类型为long的变量时，Java序列化机制会根据编译的class自动生成一个serialVersionUID作序列化版本比较用，这种情况下，只有同一次编译生成的class才会生成相同的serialVersionUID 。

如果我们不希望通过编译来强制划分软件版本，即实现序列化接口的实体能够兼容先前版本，未作更改的类，就需要显式地定义一个名为serialVersionUID，类型为long的变量，不修改这个变量值的序列化实体都可以相互进行串行化和反串行化


115. Override和Overload的区别，分别用在什么场景
116. java的反射是如何实现的

运行时类型识别(Run-time Type Identification, RTTI)主要有两种方式，一种是我们在编译时和运行时已经知道了所有的类型，另外一种是功能强大的“反射”机制。

要理解RTTI在Java中的工作原理，首先必须知道类型信息在运行时是如何表示的，这项工作是由“Class对象”完成的，它包含了与类有关的信息。类是程序的重要组成部分，每个类都有一个Class对象，每当编写并编译了一个新类就会产生一个Class对象，它被保存在一个同名的.class文件中。在运行时，当我们想生成这个类的对象时，运行这个程序的Java虚拟机(JVM)会确认这个类的Class对象是否已经加载，如果尚未加载，JVM就会根据类名查找.class文件，并将其载入，一旦这个类的Class对象被载入内存，它就被用来创建这个类的所有对象。一般的RTTI形式包括三种：

1.传统的类型转换。如“(Apple)Fruit”，由RTTI确保类型转换的正确性，如果执行了一个错误的类型转换，就会抛出一个ClassCastException异常。

2.通过Class对象来获取对象的类型。如

Class c = Class.forName(“Apple”);

Object o = c.newInstance();

3.通过关键字instanceof或Class.isInstance()方法来确定对象是否属于某个特定类型的实例，准确的说，应该是instanceof / Class.isInstance()可以用来确定对象是否属于某个特定类及其所有基类的实例，这和equals() / ==不一样，它们用来比较两个对象是否属于同一个类的实例，没有考虑继承关系。

##### java117. spring 事务传播方式
http://blog.csdn.net/maoxiao1229/article/details/7253992

[DST_LOCK]: http://surlymo.iteye.com/blog/2082684 "分布式锁的三种实现方式"
[ZOOKEEPER]: http://cailin.iteye.com/blog/2014486/ "zookeeper 原理"
[REDIS_DST_LOCK]: http://blog.csdn.net/java2000_wl/article/details/8740911
