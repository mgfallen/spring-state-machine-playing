package config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import springStateMachine.Events;
import springStateMachine.States;

@Configuration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Application.class, StateMachineConfig.class);
        ctx.refresh();

        StateMachineFactory<States, Events> stateMachineFactory = ctx.getBean(StateMachineFactory.class);
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.start();
        boolean timerIsBroken = true;
        stateMachine.sendEvent(Events.TURNING_ON); // включение стиральной машины

        //можно добавить логику на проверку работающих датчиков
        stateMachine.sendEvent(Events.START_POURING); // начало заливание воды

        stateMachine.sendEvent(Events.START_WASHING); // начало основного этапа стирания

        //можно добавить логику на проверку исправности таймера
        if (timerIsBroken) {
            stateMachine.sendEvent(Events.BROKING);
            stateMachine.sendEvent(Events.EMERGENCY_OFF);
            System.out.println("NEED TO FIX WASHING MACHINE!");
        } else {
            stateMachine.sendEvent(Events.START_DRAINING);
            stateMachine.sendEvent(Events.TURNING_OFF); //штатное завершение работы стиральной машины
        }
    }
}
