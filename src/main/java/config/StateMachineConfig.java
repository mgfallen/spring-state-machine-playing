package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import springStateMachine.Events;
import springStateMachine.States;

import java.util.EnumSet;


@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.OFF)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(States.OFF).target(States.START).event(Events.TURNING_ON).and().withExternal()
                .source(States.START).target(States.POUR).event(Events.START_POURING).and().withExternal()
                .source(States.POUR).target(States.WASH).event(Events.START_WASHING).and().withExternal()
                .source(States.WASH).target(States.BROKE).event(Events.BROKING).and().withExternal()
                .source(States.BROKE).target(States.OFF).event(Events.EMERGENCY_OFF).and().withExternal()
                .source(States.WASH).target(States.DRAIN).event(Events.START_DRAINING).and().withExternal()
                .source(States.DRAIN).target(States.OFF).event(Events.TURNING_OFF);
    }

    @Bean
    public StateMachineListenerAdapter<States, Events> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State changed to [" + to.getId() + "]");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void stateExited(State<States, Events> state) {
                System.out.println("Finishing working in the [" + state.getId() + "]");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}