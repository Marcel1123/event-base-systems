package models;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public abstract class Subscription {
    protected List<ConditionalAttribute> filters;

    protected Subscription(final String sub_as_str)
    {
        //do the parsing of sub_as_str and create the list
        filters = new ArrayList<>();
        String patternString1 = "(\\w+)\\s*,\\s*([=|!|<|>]+)\\s*,\\s*\"?(.+?)\"?\\s*?\\)";
        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(sub_as_str);
        OperatorFactory op_fac = new OperatorFactory();
        while(matcher.find()) {
        System.out.println("found: " + matcher.group(1) +
                " "       + matcher.group(2)+
                " "       + matcher.group(3));
            filters.add(new ConditionalAttribute(matcher.group(1).trim(),
                    op_fac.getOperator(matcher.group(2).trim()),
                    matcher.group(3).trim())
            );
        }

    }
    public boolean match(Publication pub)
    {
        return false;
    }

    public boolean match(PublicationAvg pub){ return false;}

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Subscription)) {
            return false;
        }

        Subscription s = (Subscription) o;
        return s.filters.equals(filters);
    }


    @Override
    public int hashCode() {
        int result = 17;
        for (ConditionalAttribute filter: filters)
        {
            result = 31 * result + filter.hashCode();
        }
        return result;
    }

    @Override
    public String toString()
    {
        return this.filters.toString();
    }
}
