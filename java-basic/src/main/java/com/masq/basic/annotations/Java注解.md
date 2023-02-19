# Java注解

> Java注解是放在源码类、方法、字段、参数等前面的一种特殊的注释。
>
> 注释或被编译器直接忽略，注解则可以被编译器打包进入class文件，因此注解是一种用作标注的元数据。

## 注解的作用

注解本身对代码逻辑没有任何影响，如何使用注解完全是由工具决定。

Java注解分为三类：

1. 编译器使用的注解
   - `@Override`：让编译器检查方法是否正确的实现了覆写。
   - `@SuppressWarnings`：告诉编译器忽略此处代码产生的警告。
2. 工具处理`.class`文件使用的注解
3. 程序运行期能够读取的注解

## 定义注解

```java
public @interface Log {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
```

Java通过`@interface`语法来i定义注解，注解参数类似无参方法，建议使用`default`设定一个默认值。

### 元注解

用来修饰其他注解，Java标准库定义了一些元注解，我们只需要使用这些元注解，而不用去自己定义。

1. `@Target`：定义注解可以用于源码哪些位置

   - 类或者接口：`ElementType.TYPE`
   - 字段：`ElementType.FIELD`
   - 方法：`ElementType.METHOD`
   - 构造方法：`ElementType.CONSTRUCTOR`
   - 方法参数：`ElementType.PARAMETER`

   *如果定义多个位置*：`@Target({ElementType.TYPE, ElementType.METHOD})`

2. `@Retention`：定义注解的生命周期，如果不存在的时候默认为`CLASS`

   - 仅编译器：`RetentionPolicy.SOURCE`
- 仅`class`文件：`RetentionPolicy.CLASS`
   - 运行期：`RetentionPolicy.RUNTIME`

3. `@Repeatable`：定义注解是否可以重复，在某个地方可以重复多次，这个元注解很少用到。

4. `@Inherited`：定义子类可使用父类的注解，仅针对`@Target(ElementType.TYPE)`类型的注解有效，并且针对`class`继承，对`interface`无效。

### 定义一个注解的流程

1. 用`@interface`定义注解

   ```java
   public @interface Report {
   }
   ```

2. 添加参数、默认值 

   ```java
   public @interface Report {
    	int type() default 0;
       String level() default "info";
       String value() default "";
   }
   ```

3. 用元注解配置注解

   ```java
   @Target({ElementType.TYPE, ElementType.METHOD})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Report {
    	int type() default 0;
       String level() default "info";
       String value() default "";
   }
   ```

## 处理注解

`SOURCE`类型的注解在编译器就被丢掉了；

`CLASS`类型的注解仅保存在`class`文件中，不会被加载进入JVM中；

`RUNTIME`类型的注解会被加载进JVM，在运行期被程序读取。

我们经常用到或者编写的是`RUNTIME`类型的注解，所以主要是考虑`RUNTIME`类型注解的使用。

### 读取注解

读取注解我们需要用到反射的api：

1. 判断注解是否存在与某个`Class`、`Method`、`Field`或者`Constructor`

   ```java
   Class.isAnnotationPresent(Class);        // 判断注解是否存在与Class
   Method.isAnnotationPresent(Class);       // 判断注解是否存在Method
   Field.isAnnotationPresent(Class);        // 判断注解是否存在Field
   Constructor.isAnnotationPresent(Class);  // 判断注解是否存在Constructor
   // eg. 判断Person类是否存在Report注解
   Person.class.isAnnotationPresent(Report.class);
   ```

2. 利用反射api读取注解

   ```java
   Class.getAnnotation(Class);
   Field.getAnnotation(Class);
   Method.getAnnotation(Class);
   Constructor.getAnnotation(Class);
   // eg. 获取Person上面的Report注解
   Report report = Person.class.getAnnotation(Report.class);
   int type = report.type();
   String level = report.level();
   ```

3. 读取方法参数的注解

   由于方法的参数是一个数组，每个参数又可以有多个注解，所以一般是通过一个二维数组获取所有参数的注解：

   ```java
   public void test(@NotNull @Range(max = 5)String name, @Notnull String Prefix) {}
   // 读取该方法的注解
   // 1 获取该方法
   Method method = ....;
   // 2 获取所有注解
   Annotation[][] annotations = method.getParameterAnnotations();
   // 3 获取第一个参数注解
   Annotation[] annosForFirstParam = annotations[0];
   for (Annobation annotation : annosForFirstParam) {
       if (annotation istanceof Range) {
           Range range = (Range)annotation;
       }
       if (annotation istanceof NotNull) {
           NotNull notNull = (NotNull) annotation;
       }
   }
   ```

### 使用注解

JVM不会对注解添加任何逻辑，所以注解的逻辑需要我们自己来编写。例如如下注解，需要给`JavaBean`中的`String`类型字段限制长度：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {
    int min() default 0;
    int max() default 255;
}
```

我们可以在`JavaBean`中使用该字段：

```JAVA
package com.masq.basic.annotations;

/**
 * @title Person
 * @Author masq
 * @Date: 2021/8/31 下午2:33
 * @Version 1.0
 */
public class Person {

    @Range(min = 3, max = 25)
    private String username;

    @Range(min = 6, max = 100)
    private String password;

    @Range(min = 6)
    private String email;

    @Range(max = 11)
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

```

编写一个用来检查`Person`的`String`字段是否符合条件的方法：

```java
public void check (Person person) throws IllegalAccessException {
    Class<? extends Person> personClass = person.getClass();
    Field[] declaredFields = personClass.getDeclaredFields();
    for (Field field : declaredFields) {
        field.setAccessible(true);
        Range range = field.getAnnotation(Range.class);
        if (null != range) {
            Object o = field.get(person);
            if (o instanceof String) {
                String value = (String) o;
                if (value.length() < range.min() || value.length() > range.max()) {
                    throw new IllegalArgumentException("Invalid field: " + field.getName());
                }
            }
        }
    }
}
```

