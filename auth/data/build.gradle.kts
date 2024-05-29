plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.runique.jvm.ktor)
}

android {
    namespace = "mg.jaava.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}