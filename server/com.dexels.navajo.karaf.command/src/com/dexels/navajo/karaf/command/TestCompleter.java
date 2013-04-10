package com.dexels.navajo.karaf.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.karaf.shell.console.Completer;


/**
 * <p>
 * A very simple completer.
 * </p>
 */
public class TestCompleter implements Completer {

 /**
  * @param buffer the beginning string typed by the user
  * @param cursor the position of the cursor
  * @param candidates the list of completions proposed to the user
  */
 public int complete(String buffer, int cursor, List<String> candidates) {
    StringsCompleter delegate = new StringsCompleter();
    delegate.getStrings().add("one");
    delegate.getStrings().add("two");
    delegate.getStrings().add("three");
    return delegate.complete(buffer, cursor, candidates);
 }

}



/**
 * Completer for a set of strings.
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 * @since 2.3
 */
class StringsCompleter
    implements Completer
{
    private final SortedSet<String> strings = new TreeSet<String>();

    public StringsCompleter() {
        // empty
    }

    public StringsCompleter(final Collection<String> strings) {
//        checkNotNull(strings);
        getStrings().addAll(strings);
    }

    public StringsCompleter(final String... strings) {
        this(Arrays.asList(strings));
    }

    public Collection<String> getStrings() {
        return strings;
    }

    public int complete(final String buffer, final int cursor, final List<String> candidates) {
        // buffer could be null
//        checkNotNull(candidates);

        if (buffer == null) {
            candidates.addAll(strings);
        }
        else {
            for (String match : strings.tailSet(buffer)) {
                if (!match.startsWith(buffer)) {
                    break;
                }

                candidates.add(match);
            }
        }

        if (candidates.size() == 1) {
            candidates.set(0, candidates.get(0) + " ");
        }

        return candidates.isEmpty() ? -1 : 0;
    }
}