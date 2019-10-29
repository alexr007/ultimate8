package optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class OptionalExamples {

  public List<String> data1() {
    return new ArrayList<String>() {{
      add("One");
      add(null);
      add("Two");
      add(null);
      add(null);
      add("Three");
      add(null);
      add(null);
      add(null);
    }};
  }

  // different way of initialization
  public void ex00() {
    System.out.println("Example #0");
    // data
    String s1 = "Definitely not null value";
    String s2 = "Possibly nullable";
    String s3 = null;
    // wrappers
    Optional<String> opt1 = Optional.of(s1);
    Optional<String> opt2 = Optional.empty();
    Optional<String> opt3 = Optional.ofNullable(s2);  // Optional.empty()
    Optional<String> opt4 = Optional.ofNullable(s3);  // Optional.of
    System.out.println("----------");
  }

  // the problem
  public void ex01() {
    System.out.println("Example #1");
    List<String> data = data1();
    for (String s: data) {
      if (s != null) {
        System.out.println(s);
      }
    }
    System.out.println("----------");
  }

  // the solution
  public void ex02() {
    System.out.println("Example #2");
    data1().stream()
        .map(s -> Optional.ofNullable(s))
        .map(o -> o.map(s -> String.format("<<%s>>", s)))
        .filter(o -> o.isPresent())
        .map(o -> o.get())
        .forEach(System.out::println);
    System.out.println("----------");
  }

  String eager() {
    System.out.println("call: eager()");
    return "I'm Eager";
  }

  String lazy() {
    System.out.println("call: lazy()");
    return "I'm Lazy";
  }

  // different extractor with eager approach (used for EASY-to-calculate values)
  public void ex03() {
    System.out.println("Example #3");
    data1().stream()
        .map(s -> Optional.ofNullable(s))
        .map(o -> o.map(s -> String.format("<<%s>>", s)))
        .map(o -> o.orElse(eager()))
        .forEach(System.out::println);
    System.out.println("----------");
  }
  // different extractor with eager approach (used for HARD-to-calculate values)
  public void ex04() {
    System.out.println("Example #4");
    data1().stream()
        .map(s -> Optional.ofNullable(s))
        .map(o -> o.map(s -> String.format("<<%s>>", s)))
        .map(o -> o.orElseGet(() -> lazy()))
        .forEach(System.out::println);
    System.out.println("----------");
  }
  // different extractor with eager approach (used for problems)
  public void ex05() {
    System.out.println("Example #5");
    data1().stream()
        .map(s -> Optional.ofNullable(s))
        .map(o -> o.map(s -> String.format("<<%s>>", s)))
        .map(o -> o.orElseThrow(() -> new IllegalArgumentException("NULL was given!")))
        .forEach(System.out::println);
    System.out.println("----------");
  }
  // manual way
  public void ex06() {
    System.out.println("Example #6");
    Optional<String> opt1 = Optional.of("yes");
    Optional<String> opt2 = Optional.empty();

    opt1.ifPresent(s -> System.out.println(s)); // yes
    opt1.ifPresent(System.out::println);        // yes
    opt2.ifPresent(s -> System.out.println(s));
    opt2.ifPresent(System.out::println);

    if (opt1.isPresent()) {
      System.out.println(opt1.get());        // yes
    }
    if (opt2.isPresent()) {
      System.out.println(opt1.get());
    }
    System.out.println("----------");
  }


  public static void main(String[] args) {
    OptionalExamples ox = new OptionalExamples();
//    ox.ex01();
//    ox.ex02();
//    ox.ex03();
//    ox.ex04();
//        ox.ex05();
//    ox.ex06();
//    ox.data1().stream() // Stream<String>
    List<String> data1 = ox.data1();
    Stream<String> stream = data1.stream();
    stream                                  // Stream<String>
        .map(s -> Optional.ofNullable(s))   // Stream<Optional<String>> : 9
        .filter(o -> o.isPresent())          // Stream<Optional<String>> : 3
        .map(o -> o.get())                  // Stream<String> : 3
        .map(s -> String.format("* %s", s)) // Stream<String> : 3
        .forEach(s -> System.out.println(s));

  }
}
