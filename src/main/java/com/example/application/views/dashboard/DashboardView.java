package com.example.application.views.dashboard;

import com.example.application.backend.entity.TestCase;
import com.example.application.backend.service.ReleaseContainerService;
import com.example.application.backend.service.TestCaseService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;

@PageTitle("Dashboard | Test artifact management system")
@Route(value = "Dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final ReleaseContainerService releaseContainerService;
    private final TestCaseService testCaseService;

    public DashboardView(ReleaseContainerService releaseContainerService, TestCaseService testCaseService){
        this.releaseContainerService = releaseContainerService;
        this.testCaseService = testCaseService;

        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
                getTestCaseStats(),
                getReleaseContainerChart()
        );
    }

    public Span getTestCaseStats(){
        Span stats = new Span(testCaseService.count() + " test cases");
        stats.addClassName("testcase-stats");
        return stats;
    }

    public Component getReleaseContainerChart(){
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> stats = releaseContainerService.getStats();
        stats.forEach((name, number) ->
                dataSeries.add(new DataSeriesItem(name, number)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

}
