pipeline {
    agent any

    tools {
        maven 'Maven3'   // name configured in Manage Jenkins > Tools
        jdk 'JDK17'
    }

    triggers {
        // Scheduled regression: weekdays around 2 AM
        cron('H 2 * * 1-5')
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Browser to run on')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Regression Suite') {
            steps {
                script {
                    def cmd = "mvn clean test -Dbrowser=${params.BROWSER} -Dheadless=true"
                    if (isUnix()) { sh cmd } else { bat cmd }
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: true
            // Needs the "HTML Publisher" plugin
            publishHTML(target: [
                reportDir: 'reports',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])
        }
    }
}
