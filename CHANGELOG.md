# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [v2.1.0] - 11-07-2025

### Added
- Tags to controller endpoints for better API documentation


### Changed
- Removed the deprecated modules from JwtTokenProvider, SpringSecurityConfig and EmailService
- Upgraded springboot version from 3.3.2 -> 3.5.3
- Upgraded junit version from 5.10.2 -> 5.13.3

## [v2.0.0] - 10-07-2025

### Added
- JWT authentication and authorization for customer users
- Roles table with the roles for admin and users
- User_roles mapping to automatically map new customers with user roles upon registration

### Changed
- Modified all service APIs to use JWT auth, some require admin role to be accessed(introduced RBAC)
- Updated security filters chain to protect API endpoints with JWT, reduced the permitAll() usage
- Updated the JWT authentication flow, removed the existing custom auth flow and integrated spring security for auth
- Updated adminController and moved away from a mega controller calling all the services and moved the endpoints to their respective controllers with RBAC @PreAuthorize(ADMIN)

## [v1.0.0] - 27-06-2025

### Added
Initial release with all the basic functionalities
