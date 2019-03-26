pipeline {
    
    agent any

    stages {

        stage('Test') {
            steps {
                 sh './gradlew clean test -PnexusPassword -PnexusPassword'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build -PnexusPassword -PnexusPassword'
            }
        }

        stage('Upload Archive') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD']]) {
                    sh './gradlew uploadArchives -PnexusUser=$NEXUS_USER -PnexusPassword=$NEXUS_PASSWORD'
                }
            }
        }

        stage ("Trigger iexec-core build") {
            steps {
                build job: 'iexec-core/jenkins', propagate: true, wait: false
            }
        }

        stage ("Trigger iexec-worker build") {
            steps {
                build job: 'iexec-worker/jenkins', propagate: true, wait: false
            }
        }

    }

    post {
        always {
            junit 'build/test-results/**/*.xml'
        }
    }
}