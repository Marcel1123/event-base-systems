package models;


public class OperatorFactory {

    public AttributeOperator getOperator(String operator)
    {
        AttributeOperator ret_val = AttributeOperator.EQUAL;

        // iterate over enums using for loop
        for (AttributeOperator atr : AttributeOperator.values()) {
            if (atr.toString().equals(operator))
            {
                ret_val = atr;
                break;
            }
        }

        return ret_val;
    }
}
