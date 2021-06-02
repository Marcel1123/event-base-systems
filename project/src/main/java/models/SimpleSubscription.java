package models;


import utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class SimpleSubscription {
    List<ConditionalAttribute> filters;

    private SimpleSubscription(String sub_as_str)
    {
        //do the parsing of sub_as_str and create the list
        filters = new ArrayList<>();
        OperatorFactory op_fac = new OperatorFactory();
        filters.add(new ConditionalAttribute("city", op_fac.getOperator("="), "Iasi"));

    }

    public boolean match(Publication pub)
    {
        for(ConditionalAttribute filter: filters)
        {
            if(filter.getAttributeName().equals(Constant.StationId))
            {

            }
            else if (filter.getAttributeName().equals(Constant.City))
            {}
            else if (filter.getAttributeName().equals(Constant.Wind))
            {}
        }
        return true;
    }
}
