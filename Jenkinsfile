pipeline {
    agent any

    environment {
        //BRANCH = "${env.GIT_BRANCH.split("/")[1]}"
        BRANCH = getBranchName()
        DOCKER_REGISTRY = getDockerRegistry(BRANCH)
        DOCKER_TLS_VERIFY = "1"
        DOCKER_HOST = "tcp://192.168.99.104:2376"
        DOCKER_CERT_PATH = "/Users/aloksingh/.docker/machine/machines/default"
        DOCKER_MACHINE_NAME = "default"
        ENV_NAME = getEnvName(BRANCH)
        AWS_CLI_KEY = getAwsCliKey(BRANCH)
        AWS_CLI_SECRET = getAwsCliSecret(BRANCH)
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables - pipeline-utility-steps plugin
        //ARTIFACT = readMavenPom().getArtifactId()
        //VERSION = readMavenPom().getVersion()
        DO_NOT_SKIP_BUILD = doNotSkipBuild(BRANCH)
    }

    stages {
        stage ('Compile, Test and Package') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    sh './mvnw clean jxr:jxr verify sonar:sonar package surefire-report:report-only'
                }
            }
            post {
                always {
                    junit '**//*target/surefire-reports/*.xml'
                }
            }
        }

        stage ('Deploy Artifact') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                withMaven(maven : 'maven-3-6-3') {
                    echo "Skipping for now!"
                    //sh 'mvn deploy -DskipTests'
                }
            }
        }

        stage ('Build Docker Image app-one') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'app-one'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    echo "Building ${ARTIFACT} - ${VERSION} - ${ENV_NAME}"
                    if (BRANCH == 'master') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else if (BRANCH == 'dev') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else {
                        echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                    }
                }
            }
        }

        stage ('Push Docker Image app-one') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'app-one'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    if (BRANCH == 'master') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
                    } else if (BRANCH == 'dev') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest"
                    } else {
                        echo "Don't know which image to push ${env.BRANCH_NAME} branch"
                    }
                }
            }
        }

        stage ('Build Docker Image app-two') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'app-two'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    echo "Building ${ARTIFACT} - ${VERSION} - ${ENV_NAME}"
                    if (BRANCH == 'master') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else if (BRANCH == 'dev') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else {
                        echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                    }
                }
            }
        }

        stage ('Push Docker Image app-two') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'app-two'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    if (BRANCH == 'master') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
                    } else if (BRANCH == 'dev') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest"
                    } else {
                        echo "Don't know which image to push ${env.BRANCH_NAME} branch"
                    }
                }
            }
        }

        stage ('Build Docker Image rain-sensor') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'rain-sensor'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    echo "Building ${ARTIFACT} - ${VERSION} - ${ENV_NAME}"
                    if (BRANCH == 'master') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else if (BRANCH == 'dev') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else {
                        echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                    }
                }
            }
        }

        stage ('Push Docker Image rain-sensor') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'rain-sensor'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    if (BRANCH == 'master') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
                    } else if (BRANCH == 'dev') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest"
                    } else {
                        echo "Don't know which image to push ${env.BRANCH_NAME} branch"
                    }
                }
            }
        }

        stage ('Build Docker Image temperature-sensor') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'temperature-sensor'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    echo "Building ${ARTIFACT} - ${VERSION} - ${ENV_NAME}"
                    if (BRANCH == 'master') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else if (BRANCH == 'dev') {
                        sh "docker build -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest -t ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION} --build-arg JAR_FILE=target/${ARTIFACT}-${VERSION}.jar --build-arg ENV_NAME=${ENV_NAME} ${module}/."
                    } else {
                        echo "Don't know how to create image for ${env.GIT_BRANCH} branch"
                    }
                }
            }
        }

        stage ('Push Docker Image temperature-sensor') {
            when {
                expression {return DO_NOT_SKIP_BUILD == 'true' }
            }
            steps {
                script {
                    module = 'temperature-sensor'
                    def pom = readMavenPom file: "${module}/pom.xml"
                    ARTIFACT = pom.artifactId
                    VERSION = pom.version
                    if (BRANCH == 'master') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}:latest"
                    } else if (BRANCH == 'dev') {
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:${VERSION}"
                        sh "docker push ${DOCKER_REGISTRY}/${ARTIFACT}-dev:latest"
                    } else {
                        echo "Don't know which image to push ${env.BRANCH_NAME} branch"
                    }
                }
            }
        }
    }
}


def getBranchName() {
    if (env.BRANCH_NAME.startsWith('PR')) {
        return "${env.CHANGE_BRANCH}"
    } else {
        return "${env.BRANCH_NAME}"
    }
}

def getEnvName(branchName) {
    if( branchName == "master") {
        return "prod";
    } else if (branchName == "dev") {
        return "dev";
    } else {
        return "future";
    }
}

def doNotSkipBuild(branchName) {
    echo "Branch Name: ${branchName}"
    if( branchName == "master") {
        echo "Master"
        return 'true';
    } else if (branchName == "dev") {
        echo "Dev"
        return 'true';
    } else {
        echo "Other"
        return 'false';
    }
}

def getDockerRegistry(branchName) {
    if( branchName == "master") {
        return "alokkusingh";
    } else if (branchName == "dev") {
        return "alokkusingh";
    } else {
        return "unknown";
    }
}

def getAwsCliKey(branchName) {
    if( branchName == "master") {
        return "";
    } else if (branchName == "dev") {
        return "";
    } else {
        return "unknown";
    }
}

def getAwsCliSecret(branchName) {
    if( branchName == "master") {
        return "";
    } else if (branchName == "dev") {
        return "";
    } else {
        return "unknown";
    }
}

