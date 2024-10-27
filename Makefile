### VARIABLES ###

JC = javac
JCFLAGS = -d . -classpath .

JVM = java
JVMFLAGS = -cp "../build:../res/mariadb.jar"
BUILD_DIR = build
SRC_DIR = src
MAIN_CLASS = fr.iutfbleau.projet.Main
JAR_NAME = projet.jar

### RÈGLES ESSENTIELLES ###

# Cible par défaut
all: compile jar

# Compilation de tous les fichiers en une seule commande
compile:
	@mkdir -p $(BUILD_DIR)
	cd $(BUILD_DIR) && \
	$(JC) $(JCFLAGS) \
	../$(SRC_DIR)/fr/iutfbleau/projet/model/* \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/Tuiles/* \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/actions/* \
	../$(SRC_DIR)/fr/iutfbleau/projet/controller/* \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/MenuAvecSeriesBD.java \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/Jeu.java \
	../$(SRC_DIR)/fr/iutfbleau/projet/Main.java \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/ScoreTable.java \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/JlabelPerso.java \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/MyButton.java \
	../$(SRC_DIR)/fr/iutfbleau/projet/vue/BackgroundPanel.java

# Création de l'archive jar
jar:
	@cd $(BUILD_DIR) && \
	jar cfe $(JAR_NAME) $(MAIN_CLASS) fr/iutfbleau/projet/**/*.class

### RÈGLES D'EXÉCUTION ET DE NETTOYAGE ###

# Exécution du programme principal
run: all
	cd $(BUILD_DIR) && \
	$(JVM) $(JVMFLAGS) $(MAIN_CLASS)

# Nettoyage des fichiers compilés
clean:
	@rm -rf $(BUILD_DIR)

### BUTS FACTICES ###
.PHONY: all compile jar run clean
