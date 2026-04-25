importConfig 'semester.groovy'

check(
    ['efimov', 'nerlih', 'maslova'],
    ['Task_1_1', 'Task_1_2', 'Task_1_3']
)

settings {
    testTimeout 120

    gradeThreshold '5', 5
    gradeThreshold '4', 3
    gradeThreshold '3', 1

    bonus 'efimov', 'Task_1_1', 5
    bonus 'nerlih', 'Task_1_3', 10
}

