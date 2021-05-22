package factory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FactoryUI {
    class IntegerProperty {
        Integer data;

        public void setData(Integer data) {
            this.data = data;
        }

        public Integer getData() {
            return data;
        }
    }

    public FactoryUI() {
        Factory factory = new Factory();
        factory.start();

        JFrame frame = new JFrame("Factory");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        /*JButton button = new JButton("Press");
        frame.getContentPane().add(button);*/
        JLabel label = new JLabel("test");


        JSlider accessorySupplierSpeed = new JSlider(1, 10000, 500);
        accessorySupplierSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                //accessorySupplierSpeed.setData(value);
                for (int i = 0; i < factory.accessoriesSuppliersCount; i++) {
                    factory.getAccessorySupplierRunnable()[i].setTimeWait(value);
                }
                //label.setText(String.valueOf(value));
            }
        });
        JSlider engineSupplierSpeed = new JSlider(1, 10000, 500);
        engineSupplierSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                factory.getEngineSupplierRunnable().setTimeWait(value);
            }
        });
        JSlider carcaseSupplierSpeed = new JSlider(1, 10000, 500);
        carcaseSupplierSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                factory.getEngineSupplierRunnable().setTimeWait(value);
            }
        });


        JSlider dealersSpeed = new JSlider(1, 10000, 500);
        dealersSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                for (int i = 0; i < factory.dealerCount; i++) {
                    factory.getDealers()[i].setSleepTime(value);
                }
                //label.setText(String.valueOf(value));
            }
        });

        JLabel accessoryCountView = new JLabel("Accessory: "+factory.getAccessoryStorage().getAccessoryCount());
        JLabel engineCountView = new JLabel("Engine: "+factory.getEngineStorage().getEngineCount());
        JLabel carcaseCountView = new JLabel("Carcase: "+factory.getCarcaseStorage().getCarcaseCount());
        JLabel carCountView = new JLabel();
        JLabel tasksWait = new JLabel();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            accessoryCountView.setText("Accessory"+factory.getAccessoryStorage().getAccessoryCount());
            engineCountView.setText("Engine: "+factory.getEngineStorage().getEngineCount());
            carcaseCountView.setText("Carcase: "+factory.getCarcaseStorage().getCarcaseCount());
            carCountView.setText("Car: "+factory.getCarStorage().getCarCount());
            tasksWait.setText("Task wait in threadpool: "+factory.threadPool.getQueueSize());
        }, 0, 1, TimeUnit.SECONDS);
        JPanel contents = new JPanel();

        accessorySupplierSpeed.setToolTipText("Изменить скорость поставшика аксессуаров (1-10000)");
        engineSupplierSpeed.setToolTipText("Изменить скорость поставшика двигателей (1-10000)");
        carcaseSupplierSpeed.setToolTipText("Изменить скорость поставшика кузовов (1-10000)");
        dealersSpeed.setToolTipText("Изменить скорость продавцов");

        contents.add(accessorySupplierSpeed);
        contents.add(engineSupplierSpeed);
        contents.add(carcaseSupplierSpeed);
        //contents.add(label);
        contents.add(dealersSpeed);
        contents.add(accessoryCountView);
        contents.add(engineCountView);
        contents.add(carcaseCountView);
        contents.add(carCountView);
        contents.add(tasksWait);
        frame.getContentPane().add(contents);
        frame.setVisible(true);
    }
}
