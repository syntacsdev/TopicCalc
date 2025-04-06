import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * A joke program.
 * 
 * Given the names of users in a hypothetical voice channel, this program will try to predict the Topic being discussed in the voice channel.
 */
public class TopicCalc {

    // Add to this as necessary
    private static final List<Person> people = List.of(Person.SPIDERMAN, Person.MERCY_MAIN, Person.COMPSCI_NERD, Person.CHEMIST, Person.THEATER_KID, Person.ECONOMIST, Person.GRANDMA, Person.SOCIALITE);

    public static void main(String[] args) {
        play();
    }

    public static void play() {
        // welcome message
        System.out.flush();
        System.out.println("Welcome to the Topic Finder.\n");
        System.out.println("First, in a space-separated list, tell me who is in the voice channel.");
        System.out.println("Your options are as follows: " + people.toString());
        System.out.println("Alternatively, type EXIT to... well... exit. :(\n");

        // do some prep for the output
        Map<Topic, Integer> totals = new HashMap<Topic, Integer>();

        // read input
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toUpperCase();
        if (input.equalsIgnoreCase("exit")) System.exit(0);
        
        // parse input and populate the totals map
        String[] names = input.split(" ");
        for (String n : names) {
            Person person = Person.valueOf(n);
            if (person == null) {
                System.out.println("No person found by the name of \"" + n + "\". Do better.");
                continue;
            } else {
                // Add data to the hashmap
                for (Entry<Topic, Integer> entry : person.getTopicMap().entrySet()) {
                    if (!totals.containsKey(entry.getKey())) {
                        totals.put(entry.getKey(), entry.getValue());
                    } else {
                        int newValue = entry.getValue() + totals.get(entry.getKey());
                        totals.put(entry.getKey(), newValue);
                    }
                    continue;
                }
            }
        }

        // output detailed breakdown
        System.out.println("\nGot it. Here are the totals, sorted in ascending order:");
        Map<Topic, Integer> sortedTotals = MapUtil.sortByValue(totals);
        List<Topic> winners = new ArrayList<>();
        for (Entry<Topic, Integer> entry : sortedTotals.entrySet()) {
            winners.add(entry.getKey());
            System.out.println(entry.getKey().toString() + ": " + entry.getValue());
        }

        winners = winners.reversed();
        System.out.println("\nThey're probably talking about " + winners.get(0) + " right now.");
        System.out.println("If not, then " + winners.get(1) + " or " + winners.get(2) + ".");
        // System.out.println("They could be talking about " + winners.get(winners.size() - 1) + ", but it's unlikely.");

        System.out.println("\nTry again? (Y/N)");
        String in = scanner.nextLine();
        
        if (in.equalsIgnoreCase("y")) {
            System.out.println("Restarting...\n\n");
            play();
        } else {
            System.out.println("Goodbye.");
            System.exit(0);
        }
        scanner.close();
    }

    /**
     * This is the list of people whose names can be used.
     */
    enum Person {
        SPIDERMAN(Map.of(
            Topic.MARVEL_RIVALS, 10,
            Topic.ANIME, 7,
            Topic.WEED, 5,
            Topic.PETS, 4,
            Topic.GACHA_GAMES, 6,
            Topic.OVERWATCH, 2
        )),

        MERCY_MAIN(Map.of(
            Topic.PETS, 9,
            Topic.ANIME, 7,
            Topic.MARVEL_RIVALS, 7,
            Topic.GACHA_GAMES, 9,
            Topic.OVERWATCH, 5,
            Topic.POLITICS, 3
        )),

        COMPSCI_NERD(Map.of(
            Topic.POLITICS, 10,
            Topic.HISTORY, 7,
            Topic.SOULS_GAMES, 8,
            Topic.ANIME, 4,
            Topic.MATH, 4,
            Topic.LITERALLY_NOTHING, 8
        )),

        CHEMIST(Map.of(
            Topic.SOULS_GAMES, 10,
            Topic.POLITICS, 5,
            Topic.WEED, 7,
            Topic.MATH, 3,
            Topic.GACHA_GAMES, 3,
            Topic.ANIME, 2,
            Topic.LITERALLY_NOTHING, 5
        )),

        THEATER_KID(Map.of(
            Topic.MOVIES_AND_SHOWS, 10,
            Topic.PETS, 8,
            Topic.WEED, 7,
            Topic.RELATIONSHIPS, 3,
            Topic.POLITICS, 3,
            Topic.ANIME, 2,
            Topic.WORK, 5
        )),

        GRANDMA(Map.of(
            Topic.HERO_SHOOTERS, 7,
            Topic.ANIME, 7,
            Topic.MOVIES_AND_SHOWS, 6,
            Topic.SOULS_GAMES, 5,
            Topic.WORK, 5,
            Topic.RELATIONSHIPS, 3,
            Topic.GAY_SHIT, 4
        )),

        ECONOMIST(Map.of(
            Topic.ANIME, 7,
            Topic.GACHA_GAMES, 8,
            Topic.POLITICS, 7,
            Topic.HERO_SHOOTERS, 5,
            Topic.HISTORY, 4,
            Topic.MOVIES_AND_SHOWS, 4,
            Topic.LITERALLY_NOTHING, 10,
            Topic.MATH, 3
        )),
        
        SOCIALITE(Map.of(
            Topic.GACHA_GAMES, 10,
            Topic.ANIME, 8,
            Topic.WORK, 8,
            Topic.POLITICS, 6,
            Topic.WEED, 6,
            Topic.GAY_SHIT, 10
        ));

        Map<Topic, Integer> topicMap;

        private Person(Map<Topic, Integer> Topics) {
            this.topicMap = Topics;
        }

        public Map<Topic, Integer> getTopicMap() {
            return Collections.unmodifiableMap(this.topicMap);
        }
    }

    /**
     * The list of topics that a Person can have.
     */
    enum Topic {
        HERO_SHOOTERS,
        GACHA_GAMES,
        MOVIES_AND_SHOWS,
        MARVEL_RIVALS,
        OVERWATCH,
        POLITICS,
        HISTORY,
        MATH,
        SOULS_GAMES,
        PETS,
        WEED,
        WORK,
        RELATIONSHIPS,
        ANIME,
        LITERALLY_NOTHING,
        GAY_SHIT;
    }
}