const nextButton = document.getElementById("nextButton");
let selectedOption = null;

let scores = [0, 0, 0];
const questions = [
    {
        question: "Where do you usually ride?",
        answers: [
            {text: "City", score: 0},
            {text: "Long routes", score: 1},
            {text: "Off-road", score: 2},
            {text: "I like riding everywhere", score: -1}
        ]
    },
    {
        question: "What do you value most at motorcycle?",
        answers: [
            {text: "Performance", score: 0},
            {text: "Riding convenience", score: 1},
            {text: "Adaptation for harsh roads", score: 2},
            {text: "Appearance", score: -1}
        ]
    },
    {
        question: "What is your riding style?",
        answers: [
            {text: "I ride fast and overtake often", score: 0},
            {text: "Explore new roads and chill", score: 1},
            {text: "Whenever I can I go off the road", score: 2},
            {text: "I like a little bit of everything", score: -1}
        ]
    }

];

const questionTitle = document.getElementById("questionTitle");
const answerButtons = document.getElementById("answerButtons");

let currentQuestionIndex = 0;

function startQuiz() {
    scores = [0, 0, 0];
    currentQuestionIndex = 0;
    showQuestion();
}

function showQuestion() {
    resetState();
    let currentQuestion = questions[currentQuestionIndex];
    questionTitle.innerHTML = currentQuestion.question;

    currentQuestion.answers.forEach(answer => {
        const button = document.createElement("button");
        button.innerHTML = answer.text;
        button.classList.add("answer");
        answerButtons.appendChild(button);
        button.addEventListener("click", () => handleButtonClick(button));
    });
}

function resetState() {
    nextButton.style.display = "none";
    nextButton.innerHTML = "Next";
    while(answerButtons.firstChild) {
        answerButtons.removeChild(answerButtons.firstChild);
    }
}

function showScore() {
    resetState();
    nextButton.innerHTML = "Restart";
    nextButton.style.display = "block";
}

function handleNextButton() {
    currentQuestionIndex ++;
    if (currentQuestionIndex < questions.length) {
        showQuestion();
    }
    else {
        showScore();
    }
}

nextButton.addEventListener("click", () => {
    if(currentQuestionIndex < questions.length) {
        handleNextButton();
    }
    else {
        startQuiz();
    }
});

function handleButtonClick(btn) {
    const value = Number(btn.dataset.value);
    if(selectedOption === value){
        btn.classList.remove("active");
        selectedOption = null;
        btn.style.background = "#263142";
    }
    else {
        answerButtons.querySelectorAll("button").forEach (b =>{
            b.classList.remove("active");
            b.style.background = "#263142";
        })
        btn.classList.add("active");
        selectedOption = value;
        btn.style.background = "#090909";
    }
    nextButton.style.display="block";

}

startQuiz();
