package hr.fer.gymtracker.dao;


import hr.fer.gymtracker.models.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface that defines the methods for data access, modification, and deletion.
 */
public interface DAO {

    /**
     * Saves the object to the database.
     * @param o Object to be saved
     */
    void save(Object o);

    /**
     * Deletes the training session from the database.
     * @param trainingSession Training session to be deleted
     */
    void deleteTrainingSession(TrainingSession trainingSession);

    /**
     * Deletes the training template from the database.
     * @param templateId ID of the template to be deleted
     */
    void deleteTrainingTemplate(int templateId);

    /**
     * Deletes the sets from the database.
     * @param sets Sets to be deleted
     */
    void deleteSets(Collection<Set> sets);

    /**
     * Deletes the user from the database.
     * @param userId ID of the user to be deleted
     */
    void deleteUser(int userId);

    /**
     * Checks if user with given ID owns the training session with given ID.
     * @param userId ID of the user
     * @param trainingSessionId ID of the training session
     */
    boolean userAllowedToSeeTrainingSession(int userId, int trainingSessionId);

    /**
     * Checks if user with given ID owns the training template with given ID.
     * @param userId ID of the user
     * @param templateId ID of the training template
     */
    boolean userHasTemplate(int userId, int templateId);

    /**
     * Gets the user with given ID. Returns null if user does not exist.
     * @param id ID of the user
     * @return User with given ID or null if user does not exist
     */
    User getUserById(int id);

    /**
     * Gets the user with given email. Returns null if user does not exist.
     * @param email Email of the user
     * @return User with given email or null if user does not exist
     */
    User getUserByEmail(String email);

    /**
     * Gets the training session with given ID. Returns null if training session does not exist.
     * @param id ID of the training session
     * @return Training session with given ID or null if training session does not exist
     */
    TrainingSession getTrainingSessionById(int id);

    /**
     * Gets the training template with given ID. Returns null if training template does not exist.
     * @param id ID of the training template
     * @return Training template with given ID or null if training template does not exist
     */
    Set getSetById(Set.SetId id);

    /**
     * Gets the set type with given ID. Returns null if set type does not exist.
     * @param id ID of the set type
     * @return Set type with given ID or null if set type does not exist
     */
    SetType getSetTypeById(int id);

    /**
     * Gets the exercise with given ID. Returns null if exercise does not exist.
     * @param id ID of the exercise
     * @return Exercise with given ID or null if exercise does not exist
     */
    Exercise getExerciseById(int id);

    /**
     * Gets the training template with given ID. Returns null if training template does not exist.
     * @param id ID of the training template
     * @return Training template with given ID or null if training template does not exist
     */
    TrainingTemplate getTrainingTemplateById(int id);

    /**
     * Gets the exercise type with given ID. Returns null if exercise type does not exist.
     * @param id ID of the exercise type
     * @return Exercise type with given ID or null if exercise type does not exist
     */
    ExerciseType getExerciseTypeById(int id);

    /**
     * Gets all objects of type ExerciseType
     * @return All objects of type ExerciseType
     */
    List<ExerciseType> getAllExerciseTypes();

    /**
     * Gets the personal bests for the given exercise and user.
     * @param exerciseId ID of the exercise
     * @param userId ID of the user
     * @return List of personal bests for the given exercise and user
     */
    List<PersonalBest> getPersonalBests(int exerciseId, int userId);

    /**
     * Deletes all exercises from the database.
     */
    void deleteExercises();

    /**
     * Deletes the exercise with the given ID from the database.
     * @param exerciseId ID of the exercise to be deleted
     */
    void deleteExercise(int exerciseId);

    /**
     * Gets all exercises from the database.
     */
    List<Exercise> getAllExercises();

    /**
     * Gets all exercises from the database sorted by exercise type.
     * @return Map of exercises sorted by exercise type
     */
    Map<String, List<Exercise>> getSortedExercises();

    void updatePersonalBests(int exerciseId, int userId);

}
