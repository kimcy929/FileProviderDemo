## Demo the error

```com.example.fileproviderdemo E/DatabaseUtils: Writing exception to parcel
    java.lang.SecurityException: Permission Denial: reading androidx.core.content.FileProvider uri content://com.example.fileproviderdemo.provider/external_files/photo.jpg from pid=9402, uid=1000 requires the provider be exported, or grantUriPermission()
        at android.content.ContentProvider.enforceReadPermissionInner(ContentProvider.java:742)
        at android.content.ContentProvider$Transport.enforceReadPermission(ContentProvider.java:615)
        at android.content.ContentProvider$Transport.enforceFilePermission(ContentProvider.java:606)
        at android.content.ContentProvider$Transport.openTypedAssetFile(ContentProvider.java:520)
        at android.content.ContentProviderNative.onTransact(ContentProviderNative.java:307)
        at android.os.Binder.execTransactInternal(Binder.java:1021)
        at android.os.Binder.execTransact(Binder.java:994)```