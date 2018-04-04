package jeevsspring.spring.console.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

@Scope("request")
@Controller("listTableCtrl")
public class ListTableGameCtrl implements Serializable {

}
