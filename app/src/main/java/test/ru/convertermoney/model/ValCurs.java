package test.ru.convertermoney.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 28.06.2017.
 */

@Root(name="ValCurs")
public class ValCurs implements Serializable {

    @ElementList(inline=true, name="Valute")
    public List<Valute > valutes;

}
