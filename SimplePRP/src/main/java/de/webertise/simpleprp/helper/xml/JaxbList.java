package de.webertise.simpleprp.helper.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import de.webertise.simpleprp.model.User;

@XmlRootElement(name = "items")
@XmlSeeAlso({ User.class })
public class JaxbList<T> {

    protected List<T> items;

    public JaxbList() {
    }

    public JaxbList(List<T> list) {
        this.items = list;
    }

    @XmlElementRefs({ @XmlElementRef(name = "user", type = User.class) })
    public List<T> getItems() {
        return items;
    }
}