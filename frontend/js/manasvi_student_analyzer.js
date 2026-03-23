/* 2.1 Given Data Structure */
const students = [
  {
    name: "Lalit",
    marks: [
      { subject: "Math", score: 78 },
      { subject: "English", score: 82 },
      { subject: "Science", score: 74 },
      { subject: "History", score: 69 },
      { subject: "Computer", score: 88 }
    ],
    attendance: 82
  },
  {
    name: "Rahul",
    marks: [
      { subject: "Math", score: 90 },
      { subject: "English", score: 85 },
      { subject: "Science", score: 80 },
      { subject: "History", score: 76 },
      { subject: "Computer", score: 92 }
    ],
    attendance: 91
  }
];

// storing all student data in a array of objects
// Each student object contains the name, marks (array of subjects), and attendance.

/* 2.2 Function to calculate total marks*/
function calculateTotalMarks(student) {
    let totalMarks = 0;

    // Loop through each subject's marks and add to totalMarks
    for(let i = 0; i < student.marks.length; i++) {
        totalMarks += student.marks[i].score;
    }   
    return totalMarks;
}   
// print total marks
students.forEach(student => {
    const totalMarks = calculateTotalMarks(student);
    console.log(student.name + " has total marks: " + totalMarks);
});

// Function to calculate average marks
function calculateAverage(student) {
  const total = calculateTotalMarks(student);

  // divide total by number of subjects
  return total / student.marks.length;
}

// print average marks
students.forEach(student => {
  const avg = calculateAverage(student);
  console.log(student.name + " Average Marks: " + avg);
});

/*Subject with highest score */
subjects.forEach(function(sub){
    let highestScore = 0;
    let topScorer = "";

    students.forEach(function(student){
        student.marks.forEach(function(mark){
            
            // check if the subject matches and if the score is higher than the current highest score
            if(mark.subject === sub && mark.score > highestScore){
                highestScore = mark.score;
                topScorer = student.name;
            }
        });
    });
    // print the highest score and the top scorer for the subject
    console.log("Highest score in " + sub + " is " + highestScore + " by " + topScorer);
});

/* Subject-wise Average Score */
subjects.forEach(function(sub) {
  let totalScore = 0;
  let count = 0;

  //loop through each student and their marks to calculate total score and count for the subject
  students.forEach(function(student) {
    student.marks.forEach(function(mark) {

      // check if subject matches
      if (mark.subject === sub) {
        totalScore += mark.score;
        count++;
      }

    });
  });

  let average = totalScore / count;

  // print average
  console.log("Average score in " + sub + " is " + average);
});

/*Determine class topper */
function getTopper(students) {
  let topper = null;
  let highestMarks = 0;

  students.forEach(student => {
    const total = calculateTotalMarks(student);

    if (total > highestMarks) {
      highestMarks = total;
      topper = student.name;
    }
  });

  console.log(`Class Topper: ${topper} with ${highestMarks} marks`);
}
getTopper(students);

/*Adding the student data who fails the exam */
    students.push({
  name: "Aman",
  marks: [
    { subject: "Math", score: 68 },   // FAIL (≤ 40)
    { subject: "English", score: 60 },
    { subject: "Science", score: 70 },
    { subject: "History", score: 65 },
    { subject: "Computer", score: 75 }
  ],
  attendance: 60
});

students.push({
  name: "Riya",
  marks: [
    { subject: "Math", score: 80 },
    { subject: "English", score: 85 },
    { subject: "Science", score: 33 },
    { subject: "History", score: 88 },
    { subject: "Computer", score: 90 }
  ],
  attendance: 80   // FAIL (attendance < 75)
});

/*Determining pass/fail status based on marks and attendance */
students.forEach(function(student) {
  let total = 0;
  let hasFailed = false;
  let failReason = "";

  // calculate total marks and check subject failure
  student.marks.forEach(function(mark) {
    total += mark.score;

    // if any subject score ≤ 40 → Fail
    if (mark.score <= 40 && !hasFailed) {
      hasFailed = true;
      failReason = "Failed in " + mark.subject;
    }
  });

  let average = total / student.marks.length;

  // check attendance condition
  if (!hasFailed && student.attendance < 75) {
    hasFailed = true;
    failReason = "Low Attendance";
  }

  // print result
  if (hasFailed) {
    console.log(student.name + " Grade: Fail (" + failReason + ")");
  } else {
    let grade = "";

    if (average >= 85) grade = "A";
    else if (average >= 70) grade = "B";
    else if (average >= 50) grade = "C";
    else grade = "Fail";

    console.log(student.name + " Grade: " + grade);
  }
});