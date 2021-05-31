package com.example.application.views.list;

import com.example.application.backend.entity.ReleaseContainer;
import com.example.application.backend.entity.TestCase;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class TestCaseForm extends FormLayout {

    TextField execution = new TextField("Execution");
    TextField tfkName = new TextField("TFK name");
    TextField name = new TextField("Test name");
    TextField crm = new TextField("CRM");
    TextArea testData = new TextArea("Test data");
    TextArea comment = new TextArea("Comment");
    TextArea relatedStories = new TextArea("Related stories");
    TextArea relatedBugs = new TextArea("Related bugs");

    ComboBox<TestCase.Status> status = new ComboBox<>("Status");
    ComboBox<ReleaseContainer> releaseContainer = new ComboBox<>("Release container");

    /**
     * Declaring buttons
     */
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Button checkData = new Button("Check data");
    Button prepareData = new Button("Prepare data");
    Button deleteProduct = new Button ("Delete product");

    Binder<TestCase> binder = new BeanValidationBinder<>(TestCase.class);

    private TestCase testCase;

    public TestCaseForm(List<ReleaseContainer> releaseContainers) {
        addClassName("test-case-form");

        binder.bindInstanceFields(this);
        status.setItems(TestCase.Status.values());
        releaseContainer.setItems(releaseContainers);
        releaseContainer.setItemLabelGenerator(ReleaseContainer::getName);

        /**
         * Add buttons Check data, Prepare data and Delete product to the layout
         */
        HorizontalLayout checkAndTestDataButtonsLayout = new HorizontalLayout();
        checkAndTestDataButtonsLayout.add(checkData);
        checkAndTestDataButtonsLayout.add(prepareData);
        checkAndTestDataButtonsLayout.add(deleteProduct);


        add(
                execution,
                tfkName,
                name,
                crm,
                testData,
                checkAndTestDataButtonsLayout,
                comment,
                relatedStories,
                relatedBugs,
                releaseContainer,
                status,
                createButtonsLayout()
        );

        prepareData.addClickListener(click -> fireEvent(new PrepareDataEvent(this, testCase)));
        checkData.addClickListener(click -> fireEvent(new CheckDataEvent(this, testCase)));
        deleteProduct.addClickListener(click -> fireEvent(new DeleteProductEvent(this, testCase)));

    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
        binder.readBean(testCase);
    }


    private Component createButtonsLayout() {

        // adding themes to buttons
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, testCase)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }


    private void validateAndSave() {

        try {
            binder.writeBean(testCase);
            fireEvent(new SaveEvent(this, testCase));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class TestCaseFormEvent extends ComponentEvent<TestCaseForm> {
        private TestCase testCase;

        protected TestCaseFormEvent(TestCaseForm source, TestCase testCase) {
            super(source, false);
            this.testCase = testCase;
        }

        public TestCase getTestCase() {
            return testCase;
        }
    }

    public static class SaveEvent extends TestCaseFormEvent {
        SaveEvent(TestCaseForm source, TestCase testCase) {
            super(source, testCase);
        }
    }

    public static class DeleteEvent extends TestCaseFormEvent {
        DeleteEvent(TestCaseForm source, TestCase testCase) {
            super(source, testCase);
        }
    }

    public static class CloseEvent extends TestCaseFormEvent {
        CloseEvent(TestCaseForm source) {
            super(source, null);
        }
    }

    public static class PrepareDataEvent extends TestCaseFormEvent {
        PrepareDataEvent(TestCaseForm source, TestCase testCase) {
            super(source, testCase);
        }
    }

    public static class CheckDataEvent extends TestCaseFormEvent {
        CheckDataEvent(TestCaseForm source, TestCase testCase) {
            super(source, testCase);
        }
    }

    public static class DeleteProductEvent extends TestCaseFormEvent {
        DeleteProductEvent(TestCaseForm source, TestCase testCase) {
            super(source, testCase);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
