package StreamAPI;

import StreamAPI.Developer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamInfo {
    public static void main(String[] args) throws IOException {
        List<Developer> developers = new ArrayList<>();
        developers.add(new Developer("Alex", "Java", 4150));
        developers.add(new Developer("Mike", "Python", 4220));
        developers.add(new Developer("John", "C++", 3730));
        developers.add(new Developer("Bill", "PHP", 4800));
        developers.add(new Developer("Tom", "Python", 6100));
        developers.add(new Developer("Curt", "C++", 4780));
        developers.add(new Developer("Piter", "Java", 3550));
        developers.add(new Developer("Alex", "Python", 5300));
        developers.add(new Developer("Artem", "Java", 4100));
        developers.add(new Developer("Vlad", "C++", 4360));
        developers.add(new Developer("Michail", "Java", 3420));



        //collect
        List<Developer> javaDev = developers.stream()
                .filter(p -> "Java".equals(p.getLanguage()))
                .filter(d -> d.getSalary() >= 4000)
                .collect(Collectors.toList());
        javaDev.forEach(System.out::println);

        //reduce
        Stream<Developer> developerStream = Stream.of(new Developer("Kristin", "Python", 4060)
                ,new Developer("Marta", "Java", 4120),
                new Developer("Robin", "PHP", 5650));

        int sum = developerStream.reduce(0,
                (x,y)-> {
                    if(y.getSalary()<6000)
                        return x + y.getSalary();
                    else
                        return x + 0;
                },
                (x, y)->x+y);
        System.out.println(sum);

        //count
        List<Developer> javaDev2 = developers.stream()
                .filter(p -> "Java".equals(p.getLanguage()))
                .filter(d -> d.getSalary() >= 4000)
                .collect(Collectors.toList());
        System.out.println(javaDev2.stream().count());

        //max/min
        Developer maxSal = developers.stream().max((p1, p2) -> p1.getSalary().compareTo(p2.getSalary())).get();
        Developer minSal = developers.stream().min((p1, p2) -> p1.getSalary().compareTo(p2.getSalary())).get();
        System.out.println("max: " + maxSal+"\n"+"min: "+minSal);

        //findFirst
        Optional<Developer> javaDev3 = developers.stream()
                .filter(p -> "Java".equals(p.getLanguage()))
                .filter(d -> d.getSalary() >= 4000)
                .collect(Collectors.toList())
                .stream().findFirst();
        System.out.println(javaDev3.toString());

        //allMatch
        Stream<String> st=Stream.of("some","words","like","that");
        boolean anyMatch = st.anyMatch("like"::equals);
        System.out.println("anyMatch " + anyMatch);

        //forEachOrder
        Collection<StringBuilder> list = Arrays.asList(new StringBuilder("some"), new StringBuilder("words"), new StringBuilder("like"));
        list.stream().forEachOrdered((p) -> p.append("_new"));
        System.out.println("forEachOrdered = " + list);



        Map<String, Integer> map = Files.lines(Path.of("client", "1.txt"))
                .flatMap(line -> Arrays.stream(line.split(" +")))
                .map(v -> v.replaceAll("[?!;:.,—]", "").toLowerCase(Locale.ROOT))
                .filter(line -> !line.isBlank())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum));


        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> o2 - o1))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }


}