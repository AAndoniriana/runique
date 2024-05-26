plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "mg.jaava.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)

    implementation(projects.core.domain)
}