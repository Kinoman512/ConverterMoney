package test.ru.convertermoney.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by User on 28.06.2017.
 */
@Root(name="Valute")
public class Valute  implements Serializable {

    @Attribute(name="ID")
    public String ID;

    @Element(name="NumCode")
    public String NumCode;

    @Element(name="CharCode")
    public String CharCode;

    @Element( name="Nominal")
    public String Nominal;

    @Element( name="Name")
    public String Name;

    @Element( name="Value")
    public String Value;

}
