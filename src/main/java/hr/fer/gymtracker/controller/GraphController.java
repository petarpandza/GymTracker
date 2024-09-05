package hr.fer.gymtracker.controller;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.Set;
import hr.fer.gymtracker.models.TrainingSession;
import hr.fer.gymtracker.models.User;
import jakarta.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/graphics")
public class GraphController {

    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color BG_COLOR = new Color(18, 18, 18);
    private static final Color CONTAINER_COLOR = new Color(30, 30, 30);

    @GetMapping(
            value = "/workouts",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] workouts(HttpSession session) throws IOException {

        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);

        DefaultCategoryDataset ds = recentWorkoutsDataset(user.getTrainingSessions());

        JFreeChart chart = ChartFactory.createBarChart(
                "",
                "",
                "Num. of workouts",
                ds,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        chart.setBackgroundPaint(BG_COLOR);
        chart.getCategoryPlot().setBackgroundPaint(BG_COLOR);
        chart.getCategoryPlot().setOutlinePaint(WHITE);

        CategoryPlot plot = chart.getCategoryPlot();

        plot.getRenderer().setSeriesPaint(0, new Color(255, 255, 0));

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        var domainAxis = plot.getDomainAxis();

        rangeAxis.setTickUnit(new NumberTickUnit(1));
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickLabelPaint(WHITE);
        rangeAxis.setLabelPaint(WHITE);

        domainAxis.setTickLabelPaint(WHITE);

        return ChartUtilities.encodeAsPNG(chart.createBufferedImage(800, 300));
    }

    @GetMapping(
            value = "/personalBests",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] personalBests(@RequestParam("id") int exerciseId,
                                              HttpSession session) throws IOException {

        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);

        JFreeChart chart = ChartFactory.createLineChart(
                "",
                "",
                "Weight (kg)",
                personalBestsDataset(user.getTrainingSessions(), exerciseId),
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        chart.setBackgroundPaint(CONTAINER_COLOR);
        chart.getCategoryPlot().setBackgroundPaint(CONTAINER_COLOR);
        chart.getCategoryPlot().setOutlinePaint(WHITE);

        CategoryPlot plot = chart.getCategoryPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        var domainAxis = plot.getDomainAxis();

        rangeAxis.setTickLabelPaint(WHITE);
        rangeAxis.setTickLabelFont(new Font("Arial", Font.BOLD, 14));
        rangeAxis.setLabelPaint(WHITE);
        rangeAxis.setLabelFont(new Font("Arial", Font.BOLD, 17));

        domainAxis.setTickLabelPaint(WHITE);

        plot.getRenderer().setSeriesStroke(0, new BasicStroke(4.0f));

        return ChartUtilities.encodeAsPNG(chart.createBufferedImage(900, 300));
    }

    private DefaultCategoryDataset recentWorkoutsDataset(List<TrainingSession> workouts) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (workouts == null || workouts.isEmpty()) {
            return dataset;
        }

        Map<Integer, Integer> weeklyWorkouts = new HashMap<>();

        LocalDate currentDate = LocalDate.now();
        LocalDate startOfCurrentWeek = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        for (TrainingSession session : workouts) {
            String startTime = session.getStartTime();
            LocalDate sessionDate = LocalDate.parse(startTime.substring(0, 10)); // Extract the date part

            LocalDate startOfSessionWeek = sessionDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            long weekNumber = ChronoUnit.WEEKS.between(startOfSessionWeek, startOfCurrentWeek);

            if (weekNumber >= 0 && weekNumber < 6) {
                weeklyWorkouts.put((int) weekNumber, weeklyWorkouts.getOrDefault((int) weekNumber, 0) + 1);
            } else {
                break;
            }
        }

        for (int difference = 5; difference >= 1; difference--) {
            int workoutCount = weeklyWorkouts.getOrDefault(difference, 0);
            dataset.addValue(workoutCount, "workouts", difference + " week(s) ago");
        }

        int workoutCount = weeklyWorkouts.getOrDefault(0, 0);

        dataset.addValue(workoutCount, "workouts", "This week");


        return dataset;
    }

    private DefaultCategoryDataset personalBestsDataset(List<TrainingSession> workouts, int exerciseId) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (workouts == null || workouts.isEmpty()) {
            return dataset;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusMonths(11).withDayOfMonth(1);

        TreeMap<YearMonth, Double> personalBests = new TreeMap<>();
        for (int i = 0; i < 12; i++) {
            YearMonth yearMonth = YearMonth.from(startDate.plusMonths(i));
            personalBests.put(yearMonth, 0.0);
        }

        for (TrainingSession workout : workouts) {
            LocalDate workoutDate = LocalDate.parse(workout.getStartTime().substring(0, 10)); // Extract the date part
            YearMonth workoutMonthYear = YearMonth.from(workoutDate);

            if (workoutMonthYear.isBefore(YearMonth.from(startDate))) {
                break;
            }

            double personalBest = personalBests.get(workoutMonthYear);
            for (Set set : workout.getSets()) {
                if (set.getExercise().getExerciseId() == exerciseId) {
                    double weight = set.getWeight();
                    if (weight > personalBest) {
                        personalBest = weight;
                    }
                }
            }
            personalBests.put(workoutMonthYear, personalBest);
        }

        double lastBest = 0.0;
        for (Map.Entry<YearMonth, Double> entry : personalBests.entrySet()) {
            double currentBest = entry.getValue();
            if (currentBest < lastBest) {
                entry.setValue(lastBest);
            } else {
                lastBest = currentBest;
            }
        }

        personalBests.entrySet().removeIf(entry -> entry.getValue() == 0.0);

        for (Map.Entry<YearMonth, Double> entry : personalBests.entrySet()) {
            String yearMonth = entry.getKey().toString();
            dataset.addValue(entry.getValue(), "weight", yearMonth.substring(2));
        }

        return dataset;
    }


}
