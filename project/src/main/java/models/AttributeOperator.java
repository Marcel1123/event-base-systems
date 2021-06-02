package models;

public enum AttributeOperator {
    EQUAL{
        @Override
        public String toString()
        {
            return "=";
        }
    },
    LESS{
        @Override
        public String toString()
        {
            return "<";
        }
    },
    LESS_EQ{
        @Override
        public String toString()
        {
            return "<=";
        }
    },
    GREATER{
        @Override
        public String toString()
        {
            return ">";
        }
    },
    GREATER_EQ{
        @Override
        public String toString()
        {
            return ">=";
        }
    },
    NOT_EQ{
        @Override
        public String toString()
        {
            return "!=";
        }
    };
}
