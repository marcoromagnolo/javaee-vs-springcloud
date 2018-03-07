package jeevsspring.wildfly.poker.console.bean;

import jeevsspring.wildfly.poker.common.TableSettings;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("tableGame")
@RequestScoped
public class TableGameBean extends TableSettings implements Serializable {
}
