const BASE_URL = "http://localhost:8081/api";

// ── Storage: localStorage so it persists across tabs ──
function saveToken(token) { localStorage.setItem("token", token); }
function getToken()  {
    const t = localStorage.getItem("token");
    if (!t || t === "undefined" || t === "null") return null;
    return t;
}
function saveUser(u) { localStorage.setItem("user", JSON.stringify(u)); }
function getUser()   {
    try {
        const u = localStorage.getItem("user");
        if (!u || u === "undefined" || u === "null") return null;
        return JSON.parse(u);
    } catch(e) { return null; }
}
function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.href = "login.html";
}

// ── Auth ──
async function registerUser(name, email, password, role) {
    const res = await fetch(`${BASE_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password, role })
    });
    const text = await res.text();
    if (!res.ok) throw new Error(text || "Registration failed");
    return text;
}

async function loginUser(email, password) {
    const res = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    });
    if (!res.ok) throw new Error("Invalid credentials!");
    return await res.json();
}

// ── Quizzes ──
async function getAllQuizzes() {
    const res = await fetch(`${BASE_URL}/quiz/all`, {
        headers: { "Authorization": "Bearer " + getToken() }
    });
    const data = await res.json();
    return data.map(q => ({
        id: q.id,
        title: q.title,
        topic: q.topic,
        timeLimit: q.timeLimit,
        passingScore: q.passingScore
    }));
}

async function getQuizById(quizId) {
    const res = await fetch(`${BASE_URL}/quiz/all`, {
        headers: { "Authorization": "Bearer " + getToken() }
    });
    const data = await res.json();
    const quiz = data.find(q => q.id == quizId);
    if (!quiz) throw new Error("Quiz not found");
    return quiz;
}

async function getQuestions(quizId) {
    const res = await fetch(`${BASE_URL}/quiz/all`, {
        headers: { "Authorization": "Bearer " + getToken() }
    });
    const data = await res.json();
    const quiz = data.find(q => q.id == quizId);
    if (!quiz) throw new Error("Quiz not found");
    return quiz.questions || [];
}

// ── Submit ──
async function submitQuiz(quizId, userId, answers) {
    const convertedAnswers = {};
    Object.keys(answers).forEach(k => {
        convertedAnswers[parseInt(k)] = answers[k];
    });

    const payload = {
        quizId:  parseInt(quizId),
        userId:  parseInt(userId),
        answers: convertedAnswers
    };

    console.log("Submitting:", JSON.stringify(payload));

    const res = await fetch(`${BASE_URL}/quiz/submit`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + getToken()
        },
        body: JSON.stringify(payload)
    });

    const text = await res.text();
    console.log("Response:", text);

    if (!res.ok) throw new Error("Error " + res.status + ": " + text);

    try { return JSON.parse(text); }
    catch { throw new Error("Invalid response: " + text); }
}

// ── Certificate ──
async function downloadCertificate(attemptId) {
    const res = await fetch(`${BASE_URL}/certificate/generate/${attemptId}`, {
        headers: { "Authorization": "Bearer " + getToken() }
    });
    if (!res.ok) throw new Error("Failed to generate certificate");
    const blob = await res.blob();
    const url  = URL.createObjectURL(blob);
    const a    = document.createElement("a");
    a.href = url;
    a.download = "QuizMaster-Certificate.pdf";
    a.click();
    URL.revokeObjectURL(url);
}

// ── Attempts ──
async function getAttemptsByUser(userId) {
    const res = await fetch(`${BASE_URL}/quiz/attempts/${userId}`, {
        headers: { "Authorization": "Bearer " + getToken() }
    });
    return await res.json();
}
