package hr.fer.gymtracker.init;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.dao.jpa.JPAEMProvider;
import hr.fer.gymtracker.models.Exercise;
import hr.fer.gymtracker.models.ExerciseType;
import hr.fer.gymtracker.models.SetType;

import java.util.ArrayList;
import java.util.List;

public class InitializeExercises {

    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading sqlite driver.");
            throw new RuntimeException(e);
        }

        var dao = DAOProvider.getDAO();

        dao.deleteExercises();

        ExerciseType barbell = new ExerciseType("Barbell exercises");
        dao.save(barbell);

        ExerciseType dumbbell = new ExerciseType("Dumbbell exercises");
        dao.save(dumbbell);

        ExerciseType machine = new ExerciseType("Machine exercises");
        dao.save(machine);

        ExerciseType bodyweight = new ExerciseType("Bodyweight exercises");
        dao.save(bodyweight);


        //SetType regular = new SetType(1, "Regular set");
        //dao.save(regular);

        //SetType dropSet = new SetType(2, "Drop set");
        //dao.save(dropSet);

        //SetType superset = new SetType(3, "Superset");
        //dao.save(superset);

        //SetType toFailure = new SetType(4, "To failure");
        //dao.save(toFailure);


        List<Exercise> exercises = new ArrayList<>();

        // Barbell exercises
        exercises.add(new Exercise("Barbell Bench Press", "Lie on a bench, lower the barbell to your chest, and push it back up.", barbell));
        exercises.add(new Exercise("Deadlift", "Lift the barbell from the ground to your hips, keeping your back straight.", barbell));
        exercises.add(new Exercise("Barbell Row", "Bend over and pull the barbell to your lower chest.", barbell));
        exercises.add(new Exercise("Overhead Press", "Stand and press the barbell from your shoulders to above your head.", barbell));
        exercises.add(new Exercise("Barbell Curl", "Stand and curl the barbell upwards, keeping your elbows close to your torso.", barbell));
        exercises.add(new Exercise("Back Squat", "Stand with a barbell on your shoulders and squat down until your thighs are parallel to the floor.", barbell));
        exercises.add(new Exercise("Front Squat", "Hold the barbell on the front of your shoulders and squat down.", barbell));
        exercises.add(new Exercise("Barbell Lunge", "Step forward into a lunge with the barbell on your shoulders.", barbell));
        exercises.add(new Exercise("Barbell Shrug", "Stand and lift your shoulders towards your ears with the barbell in your hands.", barbell));
        exercises.add(new Exercise("Barbell Hip Thrust", "Rest your upper back on a bench, place the barbell over your hips, and thrust upwards.", barbell));

        // Dumbbell exercises
        exercises.add(new Exercise("Dumbbell Bench Press", "Lie on a bench, press the dumbbells up and together above your chest.", dumbbell));
        exercises.add(new Exercise("Dumbbell Fly", "Lie on a bench and open your arms wide with dumbbells, then bring them together.", dumbbell));
        exercises.add(new Exercise("Dumbbell Row", "Bend over and pull the dumbbell to your lower chest, one arm at a time.", dumbbell));
        exercises.add(new Exercise("Dumbbell Shoulder Press", "Press the dumbbells from shoulder height to above your head.", dumbbell));
        exercises.add(new Exercise("Dumbbell Curl", "Stand and curl the dumbbells upwards, keeping your elbows close to your torso.", dumbbell));
        exercises.add(new Exercise("Dumbbell Lateral Raise", "Lift the dumbbells out to your sides until shoulder height.", dumbbell));
        exercises.add(new Exercise("Dumbbell Tricep Extension", "Extend the dumbbell overhead and lower it behind your head, then lift.", dumbbell));
        exercises.add(new Exercise("Dumbbell Lunges", "Step forward into a lunge while holding dumbbells by your sides.", dumbbell));
        exercises.add(new Exercise("Dumbbell Deadlift", "Lift the dumbbells from the ground to your hips, keeping your back straight.", dumbbell));
        exercises.add(new Exercise("Dumbbell Step-Up", "Step up onto a bench or platform while holding dumbbells by your sides.", dumbbell));

        // Machine exercises
        exercises.add(new Exercise("Leg Press", "Sit on the machine and push the platform away with your legs.", machine));
        exercises.add(new Exercise("Lat Pulldown", "Sit and pull the bar down to your chest, targeting your back.", machine));
        exercises.add(new Exercise("Chest Press", "Sit on the machine and press the handles forward to work your chest.", machine));
        exercises.add(new Exercise("Seated Row", "Pull the handles towards you while seated to target your back.", machine));
        exercises.add(new Exercise("Leg Curl", "Sit or lie on the machine and curl your legs to work the hamstrings.", machine));
        exercises.add(new Exercise("Leg Extension", "Sit on the machine and extend your legs to work the quadriceps.", machine));
        exercises.add(new Exercise("Cable Tricep Pushdown", "Push the cable bar down to work your triceps.", machine));
        exercises.add(new Exercise("Cable Bicep Curl", "Pull the cable bar upwards to work your biceps.", machine));
        exercises.add(new Exercise("Pec Deck Fly", "Sit on the machine and bring the handles together in front of you to work your chest.", machine));
        exercises.add(new Exercise("Smith Machine Squat", "Perform a squat using the Smith machine for added stability.", machine));

        // Bodyweight exercises
        exercises.add(new Exercise("Push-Up", "Place your hands on the ground and push your body up and down.", bodyweight));
        exercises.add(new Exercise("Pull-Up", "Hang from a bar wiht palms facing away from you and pull your body up until your chin is above the bar.", bodyweight));
        exercises.add(new Exercise("Chin-Up", "Hang from a bar with palms facing you and pull your body up until your chin is above the bar.", bodyweight));
        exercises.add(new Exercise("Dips", "Lower and lift your body between parallel bars, focusing on triceps and chest.", bodyweight));

        exercises.forEach(dao::save);

        JPAEMProvider.close(); // commit

    }

}
