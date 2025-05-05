from fastapi import FastAPI
from pydantic import BaseModel
from typing import List, Dict

app = FastAPI(
    title="Time Management Recommendation API",
    description="Recommande des techniques de gestion du temps à partir d’un texte libre.",
    version="1.1.0"
)

class RecommendationRequest(BaseModel):
    text: str

class RecommendationResponse(BaseModel):
    recommendation: str
    description: str
    durationSuggestion: str
    benefits: List[str]

RECOMMENDATION_KNOWLEDGE_BASE: Dict[str, RecommendationResponse] = {
    "urgent": RecommendationResponse(
        recommendation="Eisenhower Matrix",
        description="Priorisez les tâches selon leur urgence et importance.",
        durationSuggestion="Variable",
        benefits=["Hiérarchise les tâches", "Diminue le stress", "Décisions plus rapides"]
    ),
    "fatigue": RecommendationResponse(
        recommendation="Pomodoro Technique",
        description="Sessions de 25 min suivies de pauses pour maintenir votre énergie.",
        durationSuggestion="25 minutes",
        benefits=["Maintient l’énergie", "Réduit la fatigue mentale", "Productivité accrue"]
    ),
    "petite tâche": RecommendationResponse(
        recommendation="Quick Wins",
        description="Commencez par les tâches rapides pour créer une dynamique positive.",
        durationSuggestion="5-15 minutes",
        benefits=["Boost de motivation", "Diminution de la charge mentale", "Démarrage rapide"]
    ),
    "projet important": RecommendationResponse(
        recommendation="Deep Work",
        description="Travail concentré sans distraction pour produire un travail de qualité.",
        durationSuggestion="90 minutes",
        benefits=["Travail profond", "Qualité maximale", "Apprentissage accéléré"]
    ),
    "distraction": RecommendationResponse(
        recommendation="Time Boxing",
        description="Allouez un temps fixe à chaque tâche pour garder la concentration.",
        durationSuggestion="30 minutes",
        benefits=["Focus renforcé", "Moins de dispersion", "Maîtrise du temps"]
    ),
    "motivation": RecommendationResponse(
        recommendation="Eat That Frog",
        description="Commencez par la tâche la plus difficile pour impacter votre journée.",
        durationSuggestion="30-60 minutes",
        benefits=["Réduit la procrastination", "Démarre fort la journée", "Effet de levier"]
    ),
    "procrastination": RecommendationResponse(
        recommendation="5-Minute Rule",
        description="Commencez une tâche en vous engageant pour seulement 5 minutes.",
        durationSuggestion="5 minutes",
        benefits=["Démarrage facile", "Effet d'entraînement", "Brise la procrastination"]
    ),
    "perfectionnisme": RecommendationResponse(
        recommendation="Progress Over Perfection",
        description="Favorisez les itérations plutôt que d’attendre la perfection.",
        durationSuggestion="Variable",
        benefits=["Débloque l’action", "Réduit la pression", "Encourage l’amélioration continue"]
    ),
    "multitâche": RecommendationResponse(
        recommendation="Single-Tasking",
        description="Concentrez-vous sur une tâche à la fois pour maximiser l’efficacité.",
        durationSuggestion="Variable selon la tâche",
        benefits=["Concentration accrue", "Moins d’erreurs", "Gain de temps global"]
    ),
    "trop de tâches": RecommendationResponse(
        recommendation="Task Batching",
        description="Groupez les tâches similaires pour réduire la fatigue décisionnelle.",
        durationSuggestion="45-60 minutes",
        benefits=["Meilleure fluidité", "Réduction du stress", "Moins d’interruptions"]
    ),
    "stress": RecommendationResponse(
        recommendation="Mindful Planning",
        description="Planifiez avec calme et lucidité pour alléger la charge mentale.",
        durationSuggestion="15 minutes en début de journée",
        benefits=["Clarté mentale", "Prévention du surmenage", "Organisation paisible"]
    ),
    "perte de temps": RecommendationResponse(
        recommendation="Time Audit",
        description="Analysez comment vous utilisez votre temps pour identifier les pertes.",
        durationSuggestion="1 journée d’observation",
        benefits=["Prise de conscience", "Élimination des distractions", "Optimisation du planning"]
    ),
}

@app.post("/recommend", response_model=RecommendationResponse)
async def recommend(request: RecommendationRequest) -> RecommendationResponse:
    text = request.text.lower()
    for keyword, recommendation in RECOMMENDATION_KNOWLEDGE_BASE.items():
        if keyword in text:
            return recommendation

    # Recommandation par défaut si aucun mot-clé ne correspond
    return RecommendationResponse(
        recommendation="Time Blocking",
        description="Réservez des blocs horaires fixes pour chaque activité.",
        durationSuggestion="45-60 minutes",
        benefits=["Clarté", "Discipline", "Moins de distractions"]
    )

@app.get("/health")
def health_check():
    return {"status": "ok"}
