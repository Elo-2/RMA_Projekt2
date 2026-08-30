# MovieHub 🎬

MovieHub je Android aplikacija za pronalaženje, pregledavanje i pretraživanje filmova. Aplikacija omogućava korisnicima registraciju i prijavu, pregled preporučenih filmova, pretraživanje filmova, pregled detalja filma, korisnički profil, podešavanja aplikacije i chat podršku.

## 📱 Opis aplikacije

MovieHub je razvijen kao projekat iz predmeta Razvoj mobilnih aplikacija.

Cilj aplikacije je omogućiti jednostavno i pregledno korisničko iskustvo za istraživanje filmova. Korisnik nakon prijave može pregledati preporučene filmove na početnoj stranici, pregledati svih 25 filmova, koristiti pretragu i otvoriti detalje pojedinačnog filma.

Aplikacija koristi lokalnu Room bazu podataka za pohranu informacija o filmovima, dok se Firebase koristi za autentifikaciju korisnika i chat podršku.

## ✨ Funkcionalnosti

### Splash Screen

Prilikom pokretanja aplikacije prikazuje se Splash Screen sa vizuelnim identitetom aplikacije.

### 🔐 Login

Postojeći korisnici mogu se prijaviti pomoću:

- Email adrese
- Lozinke

Autentifikacija se vrši pomoću Firebase Authentication servisa.

### 📝 Registracija

Novi korisnici mogu kreirati račun unosom:

- Imena
- Email adrese
- Lozinke

Nakon uspješne registracije korisnik može pristupiti aplikaciji.

### 🏠 Home

Početna stranica prikazuje:

- Naziv aplikacije MovieHub
- Poruku dobrodošlice
- Sekciju "Preporučeno za vas"
- 5 nasumično odabranih filmova od ukupno 25 dostupnih filmova

Preporučeni filmovi se biraju iz lokalno dostupne kolekcije filmova.

### 🎬 Filmovi

Ekran Filmovi prikazuje svih 25 filmova.

Svaki film prikazuje:

- Poster
- Naziv
- Godinu izlaska
- Žanr
- Ocjenu
- Kratak opis

### 🔎 Pretraga

Korisnici mogu pretraživati filmove pomoću ključnih riječi.

Pretraga podržava:

- Naziv filma
- Žanr filma

### 🎞️ Movie Details

Klikom na film korisnik otvara ekran sa detaljima filma.

Detalji uključuju:

- Poster filma
- Naziv
- Godinu izlaska
- Žanr
- Ocjenu
- Detaljan opis

### 👤 Profile

Profile ekran omogućava korisniku pregled njegovih korisničkih informacija i funkcionalnosti povezanih sa korisničkim računom.

### ⚙️ Settings

Settings ekran omogućava korisniku prilagođavanje dostupnih postavki aplikacije.

### 💬 Chat podrška

MovieHub ima chat podršku implementiranu pomoću Firebase Realtime Database.

Korisnici mogu:

- Slati poruke
- Primati poruke
- Pregledati prethodno poslane poruke
- Vidjeti ime/email korisnika koji je poslao poruku

Poruke se u realnom vremenu učitavaju iz Firebase Realtime Database.

## 🏗️ Arhitektura

Aplikacija koristi MVVM arhitekturu.

Osnovna struktura aplikacije:

text
UI
 ↓
ViewModel
 ↓
Repository
 ↓
DAO
 ↓
Room Database
