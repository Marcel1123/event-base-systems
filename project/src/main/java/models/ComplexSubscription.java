package models;

import utils.Constant;

import java.text.ParseException;

public class ComplexSubscription extends Subscription{
    public ComplexSubscription(String sub_as_str)
    {
        super(sub_as_str);
    }

    @Override
    public boolean match(Publication pub) {
        return false;
    }

    @Override
    public boolean match(PublicationAvg pub)
    {
        {
            boolean ret_val = true;
            for(ConditionalAttribute filter: filters)
            {
                if(filter.getAttributeName().equals(Constant.AVGRain))
                {
                    if(!filter.hold(pub.getRain_avg()))
                    {
                        ret_val = false;
                        break;
                    }
                }
                else if(filter.getAttributeName().equals(Constant.AVGTemp))
                {
                    if(!filter.hold(pub.getTemp_avg()))
                    {
                        ret_val = false;
                        break;
                    }
                }
                else if(filter.getAttributeName().equals(Constant.AVGWind))
                {
                    if(!filter.hold(pub.getWind_avg()))
                    {
                        ret_val = false;
                        break;
                    }
                }
                else if(filter.getAttributeName().equals(Constant.City))
                {
                    if(!filter.hold(pub.getCity()))
                    {
                        ret_val = false;
                        break;
                    }
                }
                else
                {
                    System.out.println("Could not match: " + filter);
                }
            }
            return ret_val;
        }
    }
}
