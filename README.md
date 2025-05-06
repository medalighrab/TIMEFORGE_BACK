# ⏱️ TimeForge

TimeForge est une application web de gestion du temps, des tâches et des projets, conçue pour améliorer la productivité en entreprise. Développée avec **Spring Boot** (Back-end) et **Angular** (Front-end), elle s’adresse aux équipes cherchant à planifier, suivre et optimiser leur charge de travail.

---

## 🧩 Modules principaux

- 🔐 **Gestion des utilisateurs** : inscription, authentification, rôles (`Admin`, `Team Lead`, `Supervisor`, `Simple Worker`)
- 📆 **Gestion du temps** : suivi des heures, planification, répartition du temps
- ✅ **Gestion des tâches** : création, affectation, statut, priorité
- 📊 **Gestion des projets** : découpage en phases, attribution d’équipes, suivi d’avancement
- 🎯 **Gestion des objectifs (goals)** : définition, traduction multilingue, prévision intelligente
- 📈 **Dashboard intelligent** : résumé visuel des performances et priorités

---

## ⚙️ Technologies utilisées

### 🖥️ Front-end
- Angular 17
- Tailwind CSS
- RxJS
- Angular Material

### 🌐 Back-end
- Spring Boot 3
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Tokens)

---

## 🚀 Lancement du projet

### Prérequis

- [Node.js](https://nodejs.org/)
- [Angular CLI](https://angular.io/cli)
- [Java 17+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- MySQL Server

---

### ▶️ Lancer le back-end

```bash
cd timeforge-backend
./mvnw spring-boot:run
