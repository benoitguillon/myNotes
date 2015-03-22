Eclipse plugin development

# Platform Plug-in Developer Guide

http://help.eclipse.org/luna/nav/2

## Runtime basics

Plugin = OSGI bundle.

Plugins can define extension points that can be extended by other plugins (the other plugin adds an extension to the platform). Extension points and extensions are defined in plugin.xml.

org.eclipse.core.runtime is the plugin that aggregates all Eclipse runtime basics.

## Preferences

Preference scopes:
- Instance: stored per workspace
- Configuration: stored per installation of the platform
- Default represent default values for preferences. 

Accessing preferences: 

```
IPreferencesService service = Platform.getPreferencesService();
boolean value = service.getBoolean("com.example.myplugin", "MyPreference", true, null);
```

## Detecting content type of data stream

```
IContentTypeManager contentTypeManager = Platform.getContentTypeManager();
InputStream stream = ...;
IContentType contentType = contentTypeManager.findContentTypeFor(stream, "file.xml");
stream.close();
```

returns null of no appropriate content type can be found.

Get content description

```
IContentDescription description = contentTypeManager.getDescriptionFor(stream, "file.xml");
```

## Contribute content types

Define a new content type
```
<extension point="org.eclipse.core.runtime.contentTypes">
		<content-type 
			id="text"
			name="%textContentTypeName">
			file-extensions="txt">
			<describer class="org.eclipse.core.internal.content.TextContentDescriber"/>
		</content-type>
```

Extend an existing content type

XML content type extends text content type (it enherits all text properties)
```
<content-type 
	id="xml"
	name="%xmlContentTypeName"
	base-type="org.eclipse.core.runtime.text"
	file-extensions="xml">
	<describer class="org.eclipse.core.internal.content.XMLContentDescriber"/>
	<property name="charset" default="UTF-8"/>
</content-type>
```



