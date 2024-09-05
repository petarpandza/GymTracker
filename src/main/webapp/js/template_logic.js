function addExercise() {
    const exerciseSelect = document.getElementById('exerciseSelect');
    const exerciseId = exerciseSelect.value;
    const exerciseName = exerciseSelect.options[exerciseSelect.selectedIndex].text;

    let existing = document.getElementById(`exercise-${exerciseId}`);
    if (existing) {
        return;
    }

    const exerciseList = document.getElementById('exerciseList');
    const exerciseItem = document.createElement('div');
    exerciseItem.classList.add('exercise-item');
    exerciseItem.id = `exercise-${exerciseId}`;

    exerciseItem.innerHTML = `
                <span>${exerciseName}</span>
                <input type="hidden" name="exercise-${exerciseId}" id="exercise-${exerciseId}" value="${exerciseId}">
                <button type="button" onclick="removeExercise(${exerciseId})">-</button>
            `;

    exerciseList.appendChild(exerciseItem);
}

function removeExercise(exerciseId) {
    const exerciseItem = document.getElementById(`exercise-${exerciseId}`);
    exerciseItem.remove();
}