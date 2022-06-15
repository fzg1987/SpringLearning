1. 视频地址：https://b23.tv/O2HQfLz, Spring AOP详解及示例
2. AOP是什么
AOP：Aspect Oriented Programming，面向切面编程
3. 为什么使用AOP
解耦、增加代码重用性、增加开发效率；
4. 常用的应用场景(一般是非核心业务逻辑)
日志记录、权限认证、事务处理、效率检查
异常处理、缓存处理、数据持久化、内容分发
5. 实现的步骤
(1) 定义一个切面类Aspect
即声明的类，增加@Component@Aspect两个注解，Springboot中要引入spring-boot-start-aop依赖包；
(2)定义切点，并定义切点在那些地方执行，采用@Pointcut注解完成，如@Pointcut(public * com.xxx.xxx.*.*(...))
规则：修饰符(可以不写，但是不能用*)+返回类型+哪些包下的类+哪些方法+方法参数 "*"代表不限,".."两个代表参数不限
(3)定义Advice通知
利用通知的5种类型注解@Before、@After、@AfterReturning、@AfterThrowing、@Around来完成在某些切点的增强动作
如@Before("myPointcut()"),myPointcut为第二步骤定义的切点。
