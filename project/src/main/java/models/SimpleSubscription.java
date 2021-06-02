package models;


import utils.Constant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SimpleSubscription extends Subscription{

    public SimpleSubscription(String sub_as_str)
    {
        super(sub_as_str);
    }

    @Override
    public boolean match(Publication pub)
    {
        boolean ret_val = true;
        for(ConditionalAttribute filter: filters)
        {
            if(filter.getAttributeName().equals(Constant.StationId))
            {
                if(!filter.hold(pub.getStationId()))
                {
                    ret_val = false;
                    break;
                }
            }
            else if (filter.getAttributeName().equals(Constant.City))
            {
                if(!filter.hold(pub.getCity()))
                {
                    ret_val = false;
                    break;
                }
            }
            else if (filter.getAttributeName().equals(Constant.Temp))
            {
                if(!filter.hold(pub.getTemp()))
                {
                    ret_val = false;
                    break;
                }
            }
            else if (filter.getAttributeName().equals(Constant.Rain))
            {
                if(!filter.hold(pub.getRain()))
                {
                    ret_val = false;
                    break;
                }
            }
            else if (filter.getAttributeName().equals(Constant.Wind))
            {
                if(!filter.hold(pub.getWind()))
                {
                    ret_val = false;
                    break;
                }
            }
            else if (filter.getAttributeName().equals(Constant.Direction))
            {
                if(!filter.hold(pub.getDirection()))
                {
                    ret_val = false;
                    break;
                }
            }
            else if (filter.getAttributeName().equals(Constant.Data))
            {
                //TODO: check if this works!
                try {
                    if(!filter.hold(pub.getData()))
                    {
                        ret_val = false;
                        break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("Could not match: " + filter);
            }
        }
        return ret_val;
    }

    @Override
    public boolean match(PublicationAvg pub) {
        return false;
    }
}
