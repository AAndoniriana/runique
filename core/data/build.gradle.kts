plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "mg.jaava.core.core"
}

dependencies {
    implementation(libs.timber)

    implementation(projects.core.domain)
    implementation(projects.core.database)
}