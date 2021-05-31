package com.example.application.views.list;

import com.example.application.backend.entity.ReleaseContainer;
import com.example.application.backend.entity.TestCase;
import com.example.application.backend.service.ReleaseContainerService;
import com.example.application.backend.service.TestCaseService;
import com.example.application.backend.slack.SlackUtils;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Test cases | Test artifact management system")

public class TestCaseView extends VerticalLayout {

    private TestCaseService testCaseService;
    private final TestCaseForm testCaseForm;

    Grid<TestCase> grid = new Grid<>(TestCase.class);
    HorizontalLayout filters = new HorizontalLayout();

    TextField textField = new TextField();
    TextField filterByName = new TextField();

    public TestCaseView(TestCaseService testCaseService, ReleaseContainerService releaseContainerService) {
        this.testCaseService = testCaseService;

        /**
         * Add a CSS-class view, set size to full
         */
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        /**
         * Adding listeners to buttons
         */
        testCaseForm = new TestCaseForm(releaseContainerService.findAll());
        testCaseForm.addListener(TestCaseForm.SaveEvent.class, this::saveTestCase);
        testCaseForm.addListener(TestCaseForm.DeleteEvent.class, this::deleteTestCase);
        testCaseForm.addListener(TestCaseForm.CloseEvent.class, event -> closeEditor());
        testCaseForm.addListener(TestCaseForm.PrepareDataEvent.class, this::prepareData);
        testCaseForm.addListener(TestCaseForm.CheckDataEvent.class, this::checkData);
        testCaseForm.addListener(TestCaseForm.DeleteProductEvent.class, this::deleteProduct);

        Div content = new Div(grid, testCaseForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }


    /**
     * The following methods
     */
    private void deleteTestCase(TestCaseForm.DeleteEvent evt) {
        testCaseService.delete(evt.getTestCase());
        updateList();
        closeEditor();
    }

    private void saveTestCase(TestCaseForm.SaveEvent evt) {
        testCaseService.save(evt.getTestCase());
        updateList();
        closeEditor();
    }

    private void closeEditor(){
        testCaseForm.setTestCase(null);
        testCaseForm.setVisible(false);
        removeClassName("editing");
    }

    private void prepareData(TestCaseForm.PrepareDataEvent evt) {
        SlackUtils.sendMessage("Hi, please prepare the following test data: \n"
                + evt.getTestCase().getTestData());
    }

    private void checkData(TestCaseForm.CheckDataEvent evt) {
        SlackUtils.sendMessage("Hi, please check if there's anything on these data: \n"
                + evt.getTestCase().getTestData());
    }

    private void deleteProduct(TestCaseForm.DeleteProductEvent evt) {
        SlackUtils.sendMessage("Hi, could you please remove any product that uses these data: \n"
                + evt.getTestCase().getTestData());
    }



    private HorizontalLayout getToolbar() {
        textField.setPlaceholder("Filter by execution name");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e -> updateList());

        filterByName.setPlaceholder("Filter test name");
        filterByName.setClearButtonVisible(true);
        filterByName.setValueChangeMode(ValueChangeMode.LAZY);
        filterByName.addValueChangeListener(e -> filterTestCasesByName());

        Button addTestCaseButton = new Button("Add test case", click -> addTestCase());

        HorizontalLayout toolbar = new HorizontalLayout(textField, filterByName, addTestCaseButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    // Handles the Add testcase button
    private void addTestCase() {
        grid.asSingleSelect().clear();
        editTestCase(new TestCase());
    }

    private void filterTestCasesByName() {
        grid.setItems(testCaseService.findTestCasesByName(filterByName.getValue()));
    }

    private void updateList() {
        grid.setItems(testCaseService.findAll(textField.getValue()));
    }

    /**
     * Configure grid to display test data
     */
    private void configureGrid() {
        grid.addClassName("testcase-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("releaseContainer");
        grid.setColumns("execution",
                        "tfkName",
                        "name",
                        "crm",
                        "status");


        grid.addColumn(testCase -> {
            ReleaseContainer releaseContainer = testCase.getReleaseContainer();
            return releaseContainer == null ? "-" : releaseContainer.getName();
        }).setHeader("Release container");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editTestCase(evt.getValue()));
    }

    private void editTestCase(TestCase testCase){
        if (testCase == null) {
            closeEditor();
        } else {
            testCaseForm.setTestCase(testCase);
            testCaseForm.setVisible(true);
            addClassName("editing");
        }
    }
}
