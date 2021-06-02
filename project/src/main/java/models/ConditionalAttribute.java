package models;

import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ConditionalAttribute {
    private String attributeName;
    private AttributeOperator attributeOperator;
    private String valueToCompare;

    public ConditionalAttribute(String attributeName, AttributeOperator attributeOperator, String valueToCompare)
    {
        this.attributeName = attributeName;
        this.attributeOperator = attributeOperator;
        this.valueToCompare = valueToCompare;
    }

    @Override
    public String toString()
    {
        return "(" + attributeName + attributeOperator + valueToCompare + ")";
    }

    public boolean hold(String value)
    {
        boolean ret_val = false;
        switch (attributeOperator)
        {
            case EQUAL:
                ret_val = valueToCompare.equals(value);
                break;
            case NOT_EQ:
                ret_val = !valueToCompare.equals(value);
                break;
            case LESS:
            case LESS_EQ:
            case GREATER:
            case GREATER_EQ:
                System.out.println("Error, ConditionalAttribute unexpected call to String hold with ");
                break;
        }
        return ret_val;
    }

    public boolean hold(Integer value)
    {
        boolean ret_val = false;
        switch (attributeOperator)
        {
            case EQUAL:
                ret_val = Integer.parseInt(valueToCompare) == value;
                break;
            case LESS:
                ret_val =  value < Integer.parseInt(valueToCompare) ;
                break;
            case LESS_EQ:
                ret_val = value <= Integer.parseInt(valueToCompare);
                break;
            case GREATER:
                ret_val = value > Integer.parseInt(valueToCompare);
                break;
            case GREATER_EQ:
                ret_val = value >= Integer.parseInt(valueToCompare);
                break;
            case NOT_EQ:
                System.out.println("Error, ConditionalAttribute unexpected call to Integer Hold with ");
                break;
        }
        return ret_val;
    }

    public boolean hold(Double value)
    {
        boolean ret_val = false;
        switch (attributeOperator)
        {
            case EQUAL:
                ret_val = Double.parseDouble(valueToCompare) == value;
                break;
            case LESS:
                ret_val = value < Double.parseDouble(valueToCompare);
                break;
            case LESS_EQ:
                ret_val = value <= Double.parseDouble(valueToCompare);
                break;
            case GREATER:
                ret_val = value > Double.parseDouble(valueToCompare);
                break;
            case GREATER_EQ:
                ret_val = value >= Double.parseDouble(valueToCompare);
                break;
            case NOT_EQ:
                System.out.println("Error, ConditionalAttribute unexpected call to Double Hold with ");
                break;
        }
        return ret_val;
    }

    public boolean hold(Date value) throws ParseException {
        boolean ret_val = false;
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(valueToCompare);
        switch (attributeOperator)
        {
            case EQUAL:
                ret_val = date == value;
                break;
            case LESS:
            case LESS_EQ:
                ret_val = date.before(value);
                break;
            case GREATER:
            case GREATER_EQ:
                ret_val = date.after(value);
                break;
            case NOT_EQ:
                ret_val = date != value;
                break;
        }
        return ret_val;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ConditionalAttribute)) {
            return false;
        }

        ConditionalAttribute at = (ConditionalAttribute) o;

        return at.attributeName.equals(attributeName) &&
                at.attributeOperator == attributeOperator &&
                at.valueToCompare.equals(valueToCompare);
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + attributeName.hashCode();
        result = 31 * result + attributeOperator.hashCode();
        result = 31 * result + valueToCompare.hashCode();
        return result;
    }

}
