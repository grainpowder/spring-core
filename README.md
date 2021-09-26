# 왜 굳이 Spring을 써야 하나

**SOLID 원칙**을 준수하며 어플리케이션을 개발할 수 있게 해주는 웹 프레임워크이기 때문이다. 물론 객체지향 언어의 **다형성**만 잘 활용하면 Spring 없이도 이 원칙을 준수하며 개발을 할 수 있다. 하지만 다형성과 함께 이 프레임워크의 기능을 사용하면 더 좋은 성질을 갖는 객체를 사용하는 어플리케이션을 더 효율적으로 만들 수 있다.

## 다형성

역할(인터페이스)과 구현(구현 클래스)을 분리해서 객체를 정의했을 때, 역할에 해당하는 인터페이스에서 나타나는 성질을 말한다. 이 역할을 여러 클래스로 구현할 수 있기 때문에 이 인터페이스는 구현된 형태를 여러 개 가질 수 있다. 그래서 이런 성질을 다형성(polymorphism)이라고 부른다.

## SOLID 원칙

역할들의 협력 관계는 그대로 유지한 상태에서 구현체만 변경할 수 있는, 유연하고 확장이 용이한 객체지향 프로그램 설계를 위한 5가지 원칙을 부르는 말이다.

### Single Responsibility Principle(SRP)

한 클래스는 하나의 책임만 져야 한다는 원칙이다. 예를 들어, ETL에 필요한 모든 메서드를 한 클래스에 전부 정의하면 SRP를 위반한 것이다. Extract, Transform, Load 각각에 필요한 메서드를 각기 다른 클래스에 정의해야 한다는 원칙인 셈이다.

### Open/Closed Principle(OCP)

확장에는 열려 있고 변경에는 닫혀 있어야 한다는 원칙이다. 기능 추가를 위해 코드를 새로 만드는 건 되지만(open), 기능을 변경하기 위해서 기존 클라이언트 코드를 변경하는 일은 없어야 한다는 말이다(closed). 

### Liskov Substitution Principle(LSP)

프로그램의 변경 없이 상위 클래스 객체를 하위 클래스 객체로 교체(치환)할 수 있어야 한다는 원칙이다. 상위 클래스에 구현된 메서드를 하위 클래스에서 같은 이름과 의도된 기능으로 구현한다면 지킬 수 있다. 쉽게 말해, 인터페이스에서 의도한 대로 구현 클래스의 메서드를 만들면 된다. 

### Interface Segregation Principle(ISP)


한 클라이언트를 표현하는 인터페이스를 여러 개로 나누는 것이 기를 쓰고 하나의 인터페이스로만 만드는 것보다 더 좋다는 원칙이다. 쉽게 말해, 데이터 전처리 클라이언트의 인터페이스를 ETL 각각에 해당하는 인터페이스로 나누는 것이 좋다는 것이다.

### Dependency Inversion Principle(DIP)

프로그램이 구체화된 구현 클래스에 의존하면 안되고 추상화된 인터페이스에 의존해야 한다는 원칙이다. 캐주얼한 프로그래밍 상황에서는 구현 클래스의 생성자를 사용(즉, 의존)하는 것이 일반적이기 때문에 추상화된 인터페이스에 의존하는 것을 역전이라고 표현한 것이다. 

## 예시: 데모 프로젝트

가입한 회원의 등급에 따라 상품을 주문할 때 할인 정책을 달리 적용하는 기능을 정의한 간단한 데모 프로젝트를 생성했다.

```
git checkout afa22d
# feat: implement demo project
```

* 사용할 DB를 정의하고 직접 작동시키는 기능(Repository)과 서비스 단에서 이 기능을 조작하는 기능(Service)을 따로 정의해서 ISP를 준수했고, 이 결과 각 인터페이스는 SRP를 준수하게 되었다.
* `DiscountPolicy` 인터페이스에서 한 회원의 주문 금액에 대한 할인 금액을 산정할 의도로 discount 메서드를 정의했다. 그래서 클라이언트 코드인 `OrderApp.kt`에서 자료형이 `DiscountPolicy`인 객체를 하위의 구현 클래스 생성자를 사용해서 생성해도 discount 메서드는 의도한 대로 정상 작동한다. 즉, LSP를 준수한다.
* 하지만 구현 클래스의 생성자를 사용하는 순간 클라이언트 코드는 구현 클래스에 의존하기 때문에 DIP를 위반하게 된다. 또한, 다른 할인 정책을 적용하기 위해서는 다른 생성자를 사용해야 하기 때문에 클라이언트 코드 수정이 불가피한데, 이는 이 프로그램이 OCP를 위반하고 있다는 것을 의미한다.

## Dependency Injection

인터페이스에만 의존하는 코드를 작성하면 DIP는 지킬 수 있지만 구체적인 작업을 수행할 수 없고, 구현 클래스를 코드에 포함시키면 구체적인 작업을 수행하는 코드는 만들 수 있지만 DIP는 지킬 수 없는 딜레마에 빠진다. 그래서 DIP를 지키는 프로그램을 만들기 위해서는 클라이언트 코드 외부에서 구현 클래스에 대한 의존성을 대신 주입해줘야 한다.

```
git checkout 77675e
# imp: make dependency injector and let OrderApp follow DIP
```

* 구현 클래스의 생성자를 대신 호출하는 `AppConfig`를 정의해 클라이언트 코드에서는 구현 클래스의 생성자를 사용하지 않을 수 있도록 했다. 이에 따라 코드는 DIP를 준수하게 되고, 기능 변경을 위해서는 클라이언트 코드 대신 설정 파일을 수정하면 되기 때문에 OCP도 준수하게 된다.
* 이처럼 클라이언트 코드에 정의된 정적인 클래스 의존관계에 실제 구현 객체를 생성해서 런타임에 전달하는 동작을 의존관계 주입(**Dependency Injection**)이라고 한다. 일반적으로는 클라이언트가 자신이 사용할 서버 구현 객체를 직접 결정하지만, 이 제어 권한이 의존관계를 주입하는 클래스로 넘어갔다는 점에서 제어의 역전(**Inversion of Control**)이 일어났다고도 표현한다.
* 요약하면, 구현 클래스에 대한 클라이언트 코드의 의존성을 클라이언트 코드 외부에서 주입시켜 제어 권한을 역전시킴으로써 어플리케이션이 추상화에만 의존하도록 한다.

## 문제점

이 코드는 SOLID원칙은 지키고 있지만, 아래와 같은 문제점들이 있다.

### 구현 클래스 객체가 중복 생성될 수 있음

`MemoryMemberRepository` 인스턴스가 클라이언트 코드에서 여러 번 생성될 수 있다면 회원을 등록하고 조회하는 서비스가 각기 다른 회원 저장소를 사용할 수 있다는 위험이 존재한다. 무엇보다 클라이언트의 요청이 있을 때마다 객체가 생성된다면 서비스의 트래픽이 많아졌을 때 메모리가 부족해질 수 있다는 위험도 있다.

### 의존성 주입 파일 관리의 번거로움

클라이언트 코드에서 사용해야 하는 구현 클래스 객체가 지금처럼 4개가 아니라 수백, 수천개라면 이에 대한 의존성을 주입하는 설정 파일(여기서는 `AppConfig`) 역시 복잡해질 수 밖에 없고, 이를 관리하는 것 자체가 큰 일이 된다.

### 불필요 객체 제거 작업의 번거로움

(진행중)

# Spring 컨테이너

구현 클래스의 인스턴스 저장소라고 생각하면 편하다. 저장된 인스턴스를 빈(Bean)이라고 한다. 컨테이너에 등록된 빈을 사용하는 방식으로 프로그램을 만들면 위의 문제점들을 모두 해결할 수 있다.

## 싱글톤 컨테이너

`@Configuration`을 붙인 클래스 안에서 구현 클래스의 인스턴스를 반환하는 메서드에  `@Bean`을 붙이면 결과로 반환되는 인스턴스가 메서드의 이름을 따서 Spring 컨테이너에 저장된다(camelCase).

```
git checkout 741815
# imp: change dependency injector to Spring container
```

* 클라이언트 코드에서 필요로 하는 인스턴스를 서버에 하나만 생성해 두고, 클래스의 생성자가 호출되면 인스턴스를 추가로 생성하는 대신 생성된 인스턴스를 참조하게 하는 디자인을 **싱글톤 패턴**이라고 한다.
* Spring 컨테이너는 기본적으로 빈으로 등록한 객체를 싱글톤 취급한다. 그래서 회원 repository의 생성자가 여러 곳에서 호출되어도 이 인스턴스가 이미 Spring 컨테이너에 등록되어 있다면 등록된 인스턴스를 참조해서 사용하도록 한다. 이를 통해 앞서 밝힌 위험 요소들이 제거된다.

### 주의사항

* 싱글톤은 항상 무상태(**stateless**)이도록 디자인해야 한다. 즉, 이 인스턴스를 참조하는 클라이언트가 이 인스턴스의 attribute를 변경할 수 있도록 만들면 안된다. 만약 attribute가 변경될 수 있다면 값이 변경되기 전과 후에 인스턴스를 참조한 클라이언트들은 사실상 다른 객체를 참조한 것이고, 이에 따라 서버에서 항상 같은 객체로 존재한다는 성질을 잃어버리기 때문이다. 다른 곳에 저장된 데이터를 읽어오는 서비스를 정의하는 용도로 싱글톤을 사용하는 것이 안전하다.
* Spring이 기본적으로 제공하는 컴포넌트(ex. `@Repository`, `@Controller`)가 아닌 특수한 빈들만 수동으로 등록하는 것이 좋다. 앞서 언급했듯 프로젝트에서 사용해야 할 빈 개수가 많아지면 이 파일을 관리하는 것이 또 일이 되기 때문이다.

## 의존성 자동 주입

`@ComponentScan`으로 빈을 자동으로 찾게 해 컨테이너에 등록한 뒤 생성자의 인자에 `@Autowired`를 붙이면 구현 클래스에 대한 의존성이 자동으로 주입된다. 클래스를 추가로 만들어서 수동으로 빈을 등록하는 방법의 번거로움을 없앨 수 있다.

```
git checkout c88f57
# imp: use Autowired, Primary, Qualifier to enable automatic DI
```

* 이 예시에서는 `@Component`를 붙인 클래스를 빈으로 등록하는 것만 보여주고 있는데, 사실 컴포넌트 스캔의 대상이 되는 어노테이션이 이것만 있는 것은 아니다. 앞서 수동으로 빈을 등록할 때 사용한 `@Configuration`도 스캔의 대상이 되고, MVC 패턴에서 등장하는 service, controller 등도 스캔 대상이 된다.
* 이렇게 스캔이 완료되고 프로젝트 안의 빈이 컨테이너에 다 등록된 다음에는 `@Autowired`가 붙은 생성자 인자의 자료형에 맞는 빈이 자동으로 연결된다.
  
### 빈 조회하기

* 컴포넌트 스캔 과정에서 빈은 자신의 클래스의 이름을 딴 이름을 부여받는다(camelCase 형식). `getBean`은 첫 인자로 빈의 이름을 받을 수 있지만, 빈을 참조하는 클라이언트 코드 안에서 구현 클래스의 이름을 명시하는 것은 OCP를 위반할 여지가 있다. 그래서 인자로 클래스만 전달하는 방법을 자주 사용한다.
* 하지만 만약 다형성을 가진 인터페이스를 인자로 전달하려 한다면 이 클래스에 대응하는 빈이 여러 개 조회될 수 있다. 이 경우, 자식 구현 클래스들 중 `@Primary`를 붙인 클래스의 인스턴스가 우선적으로 조회된다. 하지만 `@Qualifier`를 통해 사용할 빈을 구체적으로 명시한 경우, 이 빈이 조회된다.
  * 위 예시에서도 원래대로라면 `@Primary`가 붙은 `RateDiscountPolicy` 클래스의 빈이 조회되어야 하지만, `FixDiscountPolicy` 클래스의 `@Qualifier`가 생성자의 인자에 명시되었기 때문에 이 클래스의 빈이 조회된다.

### 주의사항

빈 자동 탐색의 범위는 `@ComponentScan`이 붙은 클래스가 위치한 디렉토리의 하위 패키지들로 제한된다. 프로젝트 내 모든 패키지들의 빈이 탐색될 수 있도록, 왠만하면 이 클래스는 프로젝트 소스코드 최상위 디렉토리에 저장하는 것이 좋다. Spring Boot를 사용하면 프로젝트 최상단에`@SpringBootApplication`가 붙은 클래스에 메인 메서드가 디폴트로 정의되어 있는데, 이 어노테이션에 `@ComponentScan` 함께 붙어 있기 때문에 스프링 부트의 초기 설정을 그대로 사용하는 것도 좋은 방법이다.

## 빈 생명주기 지정

(진행중)
