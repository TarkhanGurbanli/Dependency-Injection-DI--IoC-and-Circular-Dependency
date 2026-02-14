# Spring Dependency Injection (DI), IoC və Circular Dependency
### Best Practices • Real Problems • Real Solutions

Bu sənəd Spring-də **Dependency Injection**, **IoC**, **Circular Dependency** problemlərini
real kod nümunələri ilə izah edir və **best practice** yanaşmaları göstərir.

---

## 1. Dependency Injection (DI) nədir?

**Dependency Injection (DI)** — bir class-ın ehtiyac duyduğu dependency-ləri
özünün `new` ilə yaratmaması, onların **kənardan verilməsi** prinsipidir.

Spring-də bu **IoC Container** tərəfindən idarə olunur.

### EN
> Dependencies are injected, not created.

### AZ
> Class-lar ehtiyac duyduğu obyektləri **yaratmır**, **alır**.

---

## 2. IoC (Inversion of Control) nədir?

**IoC** — obyektlərin lifecycle-nin (yaradılması, idarə olunması, məhv edilməsi)
bizim koddan çıxarılıb **framework-ə verilməsidir**.

Biz demirik:
```java
new Service();
```

Spring deyir:

“Mən yaradım, sən istifadə et.”

3. DI / IoC OLMASAYDI nə problemlər olardı?
   ❌ Klassik problemli yanaşma
```java
public class OrderService {
   private PaymentService paymentService = new PaymentService();
}
```

Problemlər
Problem	İzah
Tight Coupling	Class konkret implementasiyaya bağlıdır
Test yazmaq çətindir	Mock etmək olmur
Dəyişiklik baha	Kodun içini açmalısan
SOLID pozulur	Dependency Inversion pozulur
4. Loose Coupling nədir?

Loose Coupling (Zəif bağlılıq) — class-ların bir-birinin
implementasiyasını yox, abstraksiyasını tanımasıdır.

EN

Depend on abstractions, not concretions.

AZ

“Necə işlədiyini bilmə, nə etdiyini bil.”

```java
public interface PaymentService {
    void pay();
}
```

5. “Test yazmaq çətindir” nə deməkdir?

❗ Çətindir = mümkünsüz deyil

DI olmadan

Mock yoxdur

Integration test-ə məcbur qalırıq

Testlər yavaş və kövrəkdir

DI ilə

Mock mümkündür

Isolated unit test

Sürətli və stabil testlər

6. Circular Dependency nədir?
   Tərif

İki və ya daha çox bean-in bir-birinə qarşılıqlı asılı olmasıdır.

```text
AService → BService
BService → AService
```

Bu design smell-dir.

7. Circular Dependency yaradan REAL PROBLEM KOD
   ❌ Problemli kod (RUN ET – FAIL EDƏCƏK)

```java
   @Service
public class AService {

   private final BService bService;

    public AService(BService bService) {
        this.bService = bService;
    }
}

@Service
public class BService {

    private final AService aService;

    public BService(AService aService) {
        this.aService = aService;
    }
}
```

Nəticə
BeanCurrentlyInCreationException

8. Circular Dependency üçün Həll Yolları
   ✔️ Həll 1: @Lazy

```java
   @Service
public class AService {

    private final BService bService;

    public AService(@Lazy BService bService) {
        this.bService = bService;
    }
}
```


Üstünlüklər

Sürətli fix

Legacy layihələr üçün uyğundur

Mənfi tərəflər

Problemi gizlədir

Runtime-da sürpriz yarada bilər

✔️ Həll 2: ApplicationEvent (Event-driven)
İdeya

Service-lər bir-birini birbaşa çağırmır, event göndərir.

Event class

```java
public class OrderCreatedEvent {

   private final String orderId;

   public OrderCreatedEvent(String orderId) {
      this.orderId = orderId;
   }

   public String getOrderId() {
      return orderId;
   }
}
```

### Publisher (AService)
```java
@Service
public class AService {

    private final ApplicationEventPublisher publisher;

    public AService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void createOrder() {
        publisher.publishEvent(new OrderCreatedEvent("ORD-1"));
    }
}
```

### Listener (BService)
```java
@Service
public class BService {

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        System.out.println("Order received: " + event.getOrderId());
    }
}
```

Nəticə

Circular dependency yoxdur

Loose coupling

Scale-friendly dizayn

✔️ Həll 3 (ƏN DOĞRU): Dizaynı DÜZƏLTMƏK

❗ Circular dependency Spring problemi deyil
❗ Bu bizim dizayn problemimizdir

Sual

“Bu iki service həqiqətən bir-birini bilməlidirmi?”

Refactor olunmuş dizayn

```text
AService → DomainService ← BService
```

```java
@Service
public class DomainService {
    public void process() {}
}
```

Üstünlüklər

- SOLID qorunur
- Testlər rahat
- Kod oxunaqlıdır
- Production-friendly

9. Hansı zaman hansını seçməli?
   Yanaşma	Nə zaman
   @Lazy	Legacy / quick fix
   ApplicationEvent	Decoupling lazımdırsa
   Refactor	HƏMİŞƏ ən yaxşı seçim
10. Best Practices Summary

✅ Constructor Injection
✅ Loose Coupling
✅ Event-driven düşüncə
✅ Design-first yanaşma

❌ Field Injection
❌ Circular dependency-ni gizlətmək
❌ @Lazy-ni abuse etmək

11. Qızıl Qayda

“Circular dependency varsa —
problem Spring-də yox, dizayndadır.”